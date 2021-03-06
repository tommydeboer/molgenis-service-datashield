package org.molgenis.armadillo.service;

import java.io.InputStream;
import java.util.List;
import org.molgenis.armadillo.model.Workspace;
import org.springframework.http.MediaType;

/** Reads and writes data to storage. */
public interface StorageService {
  void save(InputStream is, String objectName, MediaType mediaType);

  InputStream load(String objectName);

  void delete(String objectName);

  List<Workspace> listWorkspaces(String prefix);
}
