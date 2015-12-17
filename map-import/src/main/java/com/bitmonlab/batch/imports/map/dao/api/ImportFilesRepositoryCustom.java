package com.bitmonlab.batch.imports.map.dao.api;

import java.io.File;
import java.io.IOException;

public interface ImportFilesRepositoryCustom {

	void saveFileMap(String appIdentifier, File map) throws IOException;

	void saveFileOSM(String appIdentifier, File osm) throws IOException;

}
