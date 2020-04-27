package com.insa.backend.services;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.insa.backend.dao.IIntegrantesDao;
import com.insa.backend.dao.IProyectoDao;
import com.insa.backend.models.Integrante;
import com.insa.backend.models.Proyecto;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class IntegrantesServiceImp implements IIntegrantesService {

	@Autowired
	private IIntegrantesDao integrantesDao;
	
	@Autowired
	private IProyectoDao proyectoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Integrante> finAll() {
		return (List<Integrante>) integrantesDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Integrante findById(Long id) {
		return integrantesDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Integrante save(Integrante integrante) {
		return integrantesDao.save(integrante);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		integrantesDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return integrantesDao.count();
	}

	@Override
	public boolean saveDataUploafile(MultipartFile file) {
		boolean isFlag = false;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (extension.equalsIgnoreCase("csv")) {
			isFlag = readFileCsv(file);
		} else if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
			isFlag = readFileExcel(file);
		}

		return isFlag;
	}

	private boolean readFileExcel(MultipartFile file) {
		Workbook workbook = getWorkBook(file);
		Sheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rows = sheet.iterator();
		rows.next();
		while (rows.hasNext()) {
			Row row = rows.next();
			String pro ="";
			Integrante integrante = new Integrante();
			if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				pro = String.valueOf(row.getCell(4).getNumericCellValue());
			}
			Proyecto proyecto = proyectoDao.findByNombre(pro);
			if(proyecto!= null) {
				if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
					integrante.setNombre(row.getCell(0).getStringCellValue());
				}
				if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
					integrante.setApellido(row.getCell(1).getStringCellValue());
				}
				if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
					integrante.setEmail(row.getCell(2).getStringCellValue());
				}
				if (row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING) {
					integrante.setGenero(row.getCell(3).getStringCellValue());
				}
				
				if (row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
					integrante.setProyecto(proyecto);
				}
			}
			integrantesDao.save(integrante);
		}

		return true;
	}

	private Workbook getWorkBook(MultipartFile file) {
		Workbook workbook = null;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		try {
			if (extension.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			}
			if (extension.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private boolean readFileCsv(MultipartFile file) {
		try {
			InputStreamReader reader = new InputStreamReader(file.getInputStream());
			CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();
			List<String[]> rows = csvReader.readAll();

			for (String[] row : rows) {
				Proyecto proyecto = proyectoDao.findByNombre(row[4]);
				if(proyecto!=null) {
					integrantesDao.save(new Integrante(row[0], row[1], row[2], row[3], proyecto));
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
