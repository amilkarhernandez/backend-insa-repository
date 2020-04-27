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

import com.insa.backend.dao.IProyectoDao;
import com.insa.backend.models.Proyecto;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class ProyectoServiceImp implements IProyectoService{

	@Autowired
	private IProyectoDao proyectoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> finAll() {
		return (List<Proyecto>) proyectoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Proyecto findById(Long id) {
		return proyectoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Proyecto save(Proyecto proyecto) {
		return proyectoDao.save(proyecto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		proyectoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return proyectoDao.count();
	}

	@Override
	public boolean saveDataUploafile(MultipartFile file) {
		
		boolean isFlag = false;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if(extension.equalsIgnoreCase("csv")) {
			isFlag = readFileCsv(file);
		}else if(extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
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
			Proyecto proyecto = new Proyecto();
			if(row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
				proyecto.setCodigoProyecto(row.getCell(0).getStringCellValue());	
			}
			if(row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
				proyecto.setProjNombre(row.getCell(1).getStringCellValue());	
			}
			if(row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING) {
				proyecto.setProjDescripcion(row.getCell(2).getStringCellValue());	
			}
			if(row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING) {
				proyecto.setTotalHoras(Long.parseLong(row.getCell(3).getStringCellValue()));	
			}
			if(row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {
				proyecto.setCostoTotal(Double.parseDouble(row.getCell(4).getStringCellValue()));	
			}
			proyectoDao.save(proyecto);
		}
		
		return true;
	}

	private Workbook getWorkBook(MultipartFile file) {
		Workbook workbook = null;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		try {
			if(extension.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			}
			if(extension.equalsIgnoreCase("xls")) {
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
			CSVParser parser = new CSVParserBuilder()
				    .withSeparator(';')
				    .withIgnoreQuotations(true)
				    .build();
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();
			List<String[]> rows = csvReader.readAll();
			
			for(String[] row : rows) {
				
				proyectoDao.save(new Proyecto(row[0], row[1], row[2], Long.parseLong(row[3]), Double.parseDouble(row[4])));
			}
			return true;
		} catch (Exception e) {
			return false;
		} 
		
	}

	@Override
	public Proyecto findByNombre(String cod) {
		return proyectoDao.findByNombre(cod);
	}

}
