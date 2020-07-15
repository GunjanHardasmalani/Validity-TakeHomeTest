package com.validity.csvduplicatereader.utility;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;


@Service
public class Helper {

    static List<String> fieldNames = null;
    public static final String UTF8_BOM = "\uFEFF";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public File convertMultiPartToFile(MultipartFile file) {
		File convertFile = new File(file.getOriginalFilename());
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(convertFile);

			fos.write(file.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());

		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return convertFile;
    }


    public List<Map<String, String>> readFileData(File file) {
		List<Map<String, String>> jsonArray = new ArrayList<>();
		try (FileInputStream fileStream = new FileInputStream(file);
				InputStreamReader inputStream = new InputStreamReader(fileStream, StandardCharsets.UTF_8);
				CSVReader reader = new CSVReader(inputStream)) {
			String[] fieldNamesString;
			if ((fieldNamesString = reader.readNext()) != null) {
				if (fieldNamesString[0].startsWith(UTF8_BOM)) {
					fieldNamesString[0] = fieldNamesString[0].substring(1);
				}
				fieldNames = Arrays.asList(fieldNamesString);
			}
			String[] nextLine;
			int counter = 0;
			while ((nextLine = reader.readNext()) != null) {

				List<String> list = Arrays.asList(nextLine);
				Map<String, String> obj = new LinkedHashMap<>();
				obj.put("index", String.valueOf(++counter));
				for (int i = 0; i < list.size(); i++) {
					obj.put(fieldNames.get(i), list.get(i).isEmpty() ? "" : list.get(i));
				}
				jsonArray.add(obj);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray;
    }


    public List<String> getFieldNames() {
		return fieldNames;
	}

}
