package com.aciee.shoeStore.service;

import com.aciee.shoeStore.model.ShoesEntity;
import com.aciee.shoeStore.repository.ShoesRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ShoesService {

    private final ShoesRepository shoesRepository;

    public ShoesService(ShoesRepository shoesRepository) {
        this.shoesRepository = shoesRepository;
    }

    public List<ShoesEntity> getAllShoes() {
        return shoesRepository.findAll();
    }

    public Optional<ShoesEntity> getShoeById(Long shoeId) {
        return shoesRepository.findById(shoeId);
    }

    public ShoesEntity createShoe(ShoesEntity shoe) {
        return shoesRepository.save(shoe);
    }

    public ShoesEntity updateShoe(Long shoeId, ShoesEntity updatedShoe) {
        if (shoesRepository.existsById(shoeId)) {
            updatedShoe.setShoeId(shoeId);
            return shoesRepository.save(updatedShoe);
        }
        return null;
    }

    public void importShoes(MultipartFile file) throws IOException {
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());

        if (originalFilename.toLowerCase().endsWith(".xls") || originalFilename.toLowerCase().endsWith(".xlsx")) {
            importShoesFromExcel(file.getInputStream());
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + originalFilename);
        }
    }

    public void importShoesFromExcel(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            boolean hasColumnNames = hasColumns(sheet);

            List<ShoesEntity> shoes;
            if (hasColumnNames) {
                shoes = parseExcelRowsColumns(sheet);
            } else {
                shoes = parseExcelRows(sheet);
            }
            shoes.forEach(this::createShoe);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private boolean hasColumns(Sheet sheet) {
        // Check the first row for columns
        Row headerRow = sheet.getRow(0);
        if (headerRow != null) {
            Cell secondCell = headerRow.getCell(2);
            return secondCell == null || secondCell.getCellType() != CellType.NUMERIC;
        }

        return false;
    }

    private List<ShoesEntity> parseExcelRows(Sheet sheet) {
        List<ShoesEntity> shoes = new ArrayList<>();
        for (Row row : sheet) {
            ShoesEntity shoe = new ShoesEntity();
            shoe.setBrand(row.getCell(0).getStringCellValue());
            shoe.setModel(row.getCell(1).getStringCellValue());
            shoe.setSize((float) row.getCell(2).getNumericCellValue());
            shoe.setColor(row.getCell(3).getStringCellValue());
            shoe.setPrice(row.getCell(4).getNumericCellValue());
            shoe.setQuantity((int) row.getCell(5).getNumericCellValue());
            shoe.setImageUrl(row.getCell(6).getStringCellValue());
            shoes.add(shoe);
        }
        return shoes;
    }

    private List<ShoesEntity> parseExcelRowsColumns(Sheet sheet) {
        List<ShoesEntity> shoes = new ArrayList<>();

        // Assume the first row contains column names
        Row headerRow = sheet.getRow(0);
        if (headerRow != null) {
            // Map column names to indices
            int brandIndex = findColumnIndex(headerRow, "Brand");
            int modelIndex = findColumnIndex(headerRow, "Model");
            int sizeIndex = findColumnIndex(headerRow, "Size");
            int colorIndex = findColumnIndex(headerRow, "Color");
            int priceIndex = findColumnIndex(headerRow, "Price");
            int quantityIndex = findColumnIndex(headerRow, "Quantity");
            int imageUrlIndex = findColumnIndex(headerRow, "ImageUrl");

            // Process data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    ShoesEntity shoe = new ShoesEntity();
                    shoe.setBrand(getStringValue(row.getCell(brandIndex)));
                    shoe.setModel(getStringValue(row.getCell(modelIndex)));
                    shoe.setSize((float) getNumericValue(row.getCell(sizeIndex)));
                    shoe.setColor(getStringValue(row.getCell(colorIndex)));
                    shoe.setPrice(getNumericValue(row.getCell(priceIndex)));
                    shoe.setQuantity((int) getNumericValue(row.getCell(quantityIndex)));
                    shoe.setImageUrl(getStringValue(row.getCell(imageUrlIndex)));

                    shoes.add(shoe);
                }
            }
        }

        return shoes;
    }

    private int findColumnIndex(Row headerRow, String columnName) {
        for (Cell cell : headerRow) {
            if (getStringValue(cell).equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        return -1; // Column not found
    }

    private String getStringValue(Cell cell) {
        return cell != null ? cell.getStringCellValue() : "";
    }

    private double getNumericValue(Cell cell) {
        return cell != null ? cell.getNumericCellValue() : 0.0;
    }

    public void deleteShoe(Long shoeId) {
        try {
            shoesRepository.deleteById(shoeId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void exportShoesToPdf(String filePath) {
        List<ShoesEntity> shoes = getAllShoes();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            PdfPTable table = new PdfPTable(7); // Number of columns

            addTableHeader(table);
            addRows(table, shoes);

            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            log.error(e.getMessage());
        }
    }

    public void exportShoesToExcel(String filePath) {
        List<ShoesEntity> shoes = getAllShoes();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Shoes");

            addExcelHeader(sheet);
            addExcelRows(sheet, shoes);

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Brand", "Model", "Size", "Color", "Price", "Quantity", "ImageUrl")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<ShoesEntity> shoes) {
        for (ShoesEntity shoe : shoes) {
            table.addCell(shoe.getBrand());
            table.addCell(shoe.getModel());
            table.addCell(String.valueOf(shoe.getSize()));
            table.addCell(shoe.getColor());
            table.addCell(String.valueOf(shoe.getPrice()));
            table.addCell(String.valueOf(shoe.getQuantity()));
            table.addCell(String.valueOf(shoe.getImageUrl()));
        }
    }

    private void addExcelHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Brand", "Model", "Size", "Color", "Price", "Quantity", "ImageUrl"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
    }

    private void addExcelRows(Sheet sheet, List<ShoesEntity> shoes) {
        int rowNum = 1;
        for (ShoesEntity shoe : shoes) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(shoe.getBrand());
            row.createCell(1).setCellValue(shoe.getModel());
            row.createCell(2).setCellValue(shoe.getSize());
            row.createCell(3).setCellValue(shoe.getColor());
            row.createCell(4).setCellValue(shoe.getPrice());
            row.createCell(5).setCellValue(shoe.getQuantity());
            row.createCell(6).setCellValue(shoe.getImageUrl());
        }
    }

    public List<ShoesEntity> getFilteredShoes(Double minPrice, Double maxPrice, String sortBy, Float size, String search) {
        List<ShoesEntity> shoes = getAllShoes();

        if (minPrice != null) {
            shoes = shoes.stream().filter(shoe -> shoe.getPrice() >= minPrice).collect(Collectors.toList());
        }

        if (maxPrice != null) {
            shoes = shoes.stream().filter(shoe -> shoe.getPrice() <= maxPrice).collect(Collectors.toList());
        }

        if (size != null) {
            shoes = shoes.stream().filter(shoe -> shoe.getSize().equals(size)).collect(Collectors.toList());
        }

        if ("ASC".equalsIgnoreCase(sortBy)) {
            shoes.sort(Comparator.comparing(ShoesEntity::getPrice));
        } else if ("DESC".equalsIgnoreCase(sortBy)) {
            shoes.sort(Comparator.comparing(ShoesEntity::getPrice).reversed());
        }

        if (search != null && !search.isEmpty()) {
            shoes = shoes.stream()
                    .filter(shoe -> shoe.getBrand().toLowerCase().contains(search.toLowerCase()) ||
                            shoe.getModel().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return shoes;
    }

    public List<Float> getDistinctSizes() {
        List<ShoesEntity> shoes = getAllShoes();
        return shoes.stream()
                .map(ShoesEntity::getSize)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
