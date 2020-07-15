package com.validity.csvduplicatereader.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.validity.csvduplicatereader.utility.Helper;
import com.validity.csvduplicatereader.utility.LevenshteinDistance;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public class GetDuplicatesService {

    @Autowired
    Helper helper;

    @Value("${field.threshold}")
	private String fieldThresholdMultiplier;

	@Value("${column.threshold}")
    private String columnThresholdMultiplier;

    public List<Set<Map<String, String>>> checkDuplicates(File file) {

		DoubleMetaphone doubleMetaphone = new DoubleMetaphone();

		// Reading CSV Data table
		List<Map<String, String>> data = helper.readFileData(file);
		// Get Column Headers
		List<String> columnHeaders = helper.getFieldNames();
		// Set column match threshold
		int columnThreshold = (int) (columnHeaders.size() * Float.parseFloat(columnThresholdMultiplier));
		// List containing duplicates
		List<Set<Map<String, String>>> finalList = new ArrayList<>();
		Set<Map<String, String>> setOfDuplicates = new HashSet<Map<String, String>>();

		// Set containing non duplicates
		Set<Map<String, String>> setOfNONDuplicates = new HashSet<Map<String, String>>();

		// adding initial entry for duplicate comparision
		setOfDuplicates.add(data.get(0));
		int dataSize = data.size();

		for (int i = 0; i < dataSize; i++) {

			Map<String, String> dataToCompare = data.get(i);
			if (finalList.stream().noneMatch(itemset -> itemset.contains(dataToCompare))) {
				if (i != 0) {
					setOfDuplicates = new HashSet<Map<String, String>>();
				}
				//Filter entries not identified as duplicates
				List<Map<String, String>> entries = data.stream()
						.filter(item -> item != dataToCompare
								&& finalList.stream().noneMatch(dupEntry -> dupEntry.contains(item))
								&& !setOfNONDuplicates.contains(item))
						.collect(Collectors.toList());

				for (Map<String, String> copy : entries) {
					int fieldThreshold;
					int columnMatched = 0;
					for (int j = 0; j < columnHeaders.size(); j++) {
						// Calculate field threshold
						int columnLength = dataToCompare.get(columnHeaders.get(j)).length();
						fieldThreshold = (int) (columnLength * Float.parseFloat(fieldThresholdMultiplier));

						String currentValue = dataToCompare.get(columnHeaders.get(j));
						String nextValue = copy.get(columnHeaders.get(j));

						//Calculate LevenshteinDistance for each column

						int fieldDistance=Integer.MAX_VALUE;
						if(null!=currentValue && null!=nextValue)
						fieldDistance = LevenshteinDistance.calculate(currentValue.toLowerCase(), nextValue.toLowerCase());

						//Verify Duplicates Using Double Metaphone
						if (fieldDistance <= fieldThreshold) {
							currentValue.replaceAll("[^a-zA-Z]", "");
							nextValue.replaceAll("[^a-zA-Z]", "");
							if ((!nextValue.isEmpty() && !currentValue.isEmpty())
									&& doubleMetaphone.isDoubleMetaphoneEqual(currentValue, nextValue))
								columnMatched++;
						}
					}

					//Add duplicates to list
					if (columnMatched >= columnThreshold) {
						setOfDuplicates.add(dataToCompare);
						setOfDuplicates.add(copy);
					} else {

						if (i == 0) {
							setOfDuplicates.remove(dataToCompare);
						}
					}

				}
				//Add set of non duplicates
				if (!setOfDuplicates.isEmpty())
					finalList.add(setOfDuplicates);
				else
					setOfNONDuplicates.add(dataToCompare);
			}
		}
		finalList.forEach(item -> item.stream().forEach(map -> map.remove("index")));
		setOfNONDuplicates.stream().forEach(item -> item.remove("index"));
		finalList.add(setOfNONDuplicates);
		return finalList;
	}
}
