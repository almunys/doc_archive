package com.example.docstore.document;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
  List<DocumentEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
  Optional<DocumentEntity> findByIdAndUserId(Long id, Long userId);
}

