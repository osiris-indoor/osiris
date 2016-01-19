package com.bitmonlab.osiris.imports.map.dao.api;

import java.io.File;
import java.io.IOException;

public interface ImportFilesRepositoryCustom {

	void saveFileMap(String appIdentifier, File map) throws IOException;

	void saveFileOSM(String appIdentifier, File osm) throws IOException;

	void saveFileObj(String appIdentifier, File obj) throws IOException;

}
