package com.greyhammer.erpservice.repositories;

import com.greyhammer.erpservice.models.Attachment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
}
