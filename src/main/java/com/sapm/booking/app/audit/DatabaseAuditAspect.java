package com.sapm.booking.app.audit;

import com.sapm.booking.app.model.AuditRecord;
import com.sapm.booking.app.repositories.AuditRecordRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

@Aspect
@Component
public class DatabaseAuditAspect {

    private final AuditRecordRepository auditRecordRepository;

    public DatabaseAuditAspect(AuditRecordRepository auditRecordRepository) {
        this.auditRecordRepository = auditRecordRepository;
    }

    @AfterReturning(
            pointcut = "execution(* com.sapm.booking.app.service.*Service.save(..))", returning = "savedEntity")
    public void afterSaveOperation(Object savedEntity) {
        Long entityId = getEntityId(savedEntity);
        saveAuditRecord(
                getEntityName(savedEntity),
                "CREATE",
                entityId,
                getUsername(),
                savedEntity.toString(),
                LocalDateTime.now());
    }

    @AfterReturning(
            "execution(* com.sapm.booking.app.service.*Service.update(..)) && args(entity)")
    public void afterUpdateOperation(Object entity) {
        saveAuditRecord(
                getEntityName(entity),
                "UPDATE",
                getEntityId(entity),
                getUsername(),
                entity.toString(),
                LocalDateTime.now());
    }

    @Before(
            "execution(* com.sapm.booking.app.service.*Service.delete(..)) && args(entity)")
    public void beforeDeleteOperation(Object entity) {
        saveAuditRecord(
                getEntityName(entity),
                "DELETE",
                getEntityId(entity),
                getUsername(),
                entity.toString(),
                LocalDateTime.now());
    }

    @After(
            "execution(* com.sapm.booking.app.service.*Service.delete(..)) && args(entity)")
    public void afterDeleteOperation(JoinPoint joinPoint, Object entity) {
        saveAuditRecord(
                getEntityName(entity),
                "DELETE",
                getEntityId(entity),
                getUsername(),
                entity.toString(),
                LocalDateTime.now());
    }

    @Around(
            "execution(* com.sapm.booking.app.service.*Service.delete(..)) && args(entity)")
    public Object aroundDeleteOperation(ProceedingJoinPoint joinPoint, Object entity) throws Throwable {
        // Capturar datos antes de la eliminación
        saveAuditRecord(
                getEntityName(entity),
                "DELETE",
                getEntityId(entity),
                getUsername(),
                entity.toString(),
                LocalDateTime.now());

        // Continuar con la eliminación
        return joinPoint.proceed();
    }




    // For future use when implementing security in the API gateway micro-service
    private String getUsername() {
    /*        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
        return authentication.getName(); // Returns the username of the currently authenticated user
    }*/
        return "temp-user";
    }

    private String getEntityName(Object entity) {
        return entity.getClass().getSimpleName();
    }

    private Long getEntityId(Object entity) {
        try {
            // Use reflection to find the 'id' field in the entity class
            Field idField = entity.getClass().getDeclaredField("id");

            // Make the 'id' field accessible (in case it's private or protected)
            idField.setAccessible(true);

            // Retrieve the value of the 'id' field from the entity object
            Object idValue = idField.get(entity);

            // Check if the retrieved value is not null and is of type Long
            if (idValue instanceof Long) {
                // Return the ID as a Long
                return (Long) idValue;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Handle exceptions (e.g., if the 'id' field is not found or inaccessible)
            e.printStackTrace();
            // You may choose to throw an exception or return null in case of an error
        }

        // Return null if the ID cannot be extracted
        return null;
    }

    private void saveAuditRecord(
            String tableName,
            String operation,
            Long entityId,
            String user,
            String entity,
            LocalDateTime timestamp) {
        AuditRecord auditRecord =
                AuditRecord.builder()
                        .tableName(tableName)
                        .operation(operation)
                        .entityId(entityId)
                        .loggedInUser(user)
                        .entity(entity)
                        .timestamp(timestamp)
                        .build();
        auditRecordRepository.save(auditRecord);
    }
}

