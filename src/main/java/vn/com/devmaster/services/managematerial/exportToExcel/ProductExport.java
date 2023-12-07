package vn.com.devmaster.services.managematerial.exportToExcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.domain.Product;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Component
public class ProductExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Product> products;
    public ProductExport(List<Product> products) {
        this.products = products;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("product");
    }

    public void writeHeaderRow() {
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");

        cell = row.createCell(1);
        cell.setCellValue("Tên sản phẩm");

        cell = row.createCell(2);
        cell.setCellValue("Mô tả");

        cell = row.createCell(3);
        cell.setCellValue("Ghi chú");

        cell = row.createCell(4);
        cell.setCellValue("Danh mục");

        cell = row.createCell(5);
        cell.setCellValue("Đơn giá");

        cell = row.createCell(6);
        cell.setCellValue("Số lượng");

        cell = row.createCell(7);
        cell.setCellValue("Trạng thái");

    }

    public void writeDataRows() {
        int rowCount = 1;
        for (Product product:products) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(product.getId());

            cell = row.createCell(1);
            cell.setCellValue(product.getName());

            cell = row.createCell(2);
            cell.setCellValue(product.getDescription());

            cell = row.createCell(3);
            cell.setCellValue(product.getNotes());

            cell = row.createCell(4);
            cell.setCellValue(product.getIdcategory().getName());

            cell = row.createCell(5);
            cell.setCellValue(product.getPrice());

            cell = row.createCell(6);
            cell.setCellValue(product.getQuantity());

            cell = row.createCell(7);
            if(product.getIsactive()==0) {
                cell.setCellValue("Bật");
            }
            else cell.setCellValue("Tắt");
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
