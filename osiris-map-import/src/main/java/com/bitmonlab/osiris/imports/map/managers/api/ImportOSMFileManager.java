package com.bitmonlab.osiris.imports.map.managers.api;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.bitmonlab.osiris.imports.map.exceptions.BackgroundMapBuilderException;
import com.bitmonlab.osiris.imports.map.exceptions.ExecutionNotAllowed;
import com.bitmonlab.osiris.imports.map.exceptions.GraphBuilderException;
import com.bitmonlab.osiris.imports.map.exceptions.ImportFilesException;
import com.bitmonlab.osiris.imports.map.exceptions.InternalErrorException;
import com.bitmonlab.osiris.imports.map.exceptions.ParseMapException;
import com.bitmonlab.osiris.imports.map.exceptions.QueryException;
import com.bitmonlab.osiris.imports.map.exceptions.RoutingFileNotExistsException;

public interface ImportOSMFileManager {	

	void importOSMFile(final String appIdentifier, final InputStream data, boolean bGraphBuilder) throws ExecutionNotAllowed, InternalErrorException, ParseMapException, QueryException, IOException, NoSuchAlgorithmException, BackgroundMapBuilderException, ImportFilesException, RoutingFileNotExistsException, GraphBuilderException;

}
