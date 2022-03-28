package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
}
