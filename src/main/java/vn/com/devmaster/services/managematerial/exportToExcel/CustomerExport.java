package vn.com.devmaster.services.managematerial.exportToExcel;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.domain.Customer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CustomerExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Customer> customers;


    public CustomerExport(List<Customer> customers) {
        this.customers = customers;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("customer");
    }

    public void writeHeaderRow() {
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");

        cell = row.createCell(1);
        cell.setCellValue("Tên khách hàng");

        cell = row.createCell(2);
        cell.setCellValue("Tên người dùng");

        cell = row.createCell(3);
        cell.setCellValue("Địa chỉ");

        cell = row.createCell(4);
        cell.setCellValue("Email");

        cell = row.createCell(5);
        cell.setCellValue("Số điện thoại");

        cell = row.createCell(6);
        cell.setCellValue("Trạng thái");
    }

    public void writeDataRows() {
        int rowCount = 1;
        for (Customer customer : customers) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(customer.getId());

            cell = row.createCell(1);
            cell.setCellValue(customer.getName());

            cell = row.createCell(2);
            cell.setCellValue(customer.getUsername());

            cell = row.createCell(3);
            cell.setCellValue(customer.getAddress());

            cell = row.createCell(4);
            cell.setCellValue(customer.getEmail());

            cell = row.createCell(5);
            cell.setCellValue(customer.getPhone());

            cell = row.createCell(6);
            if(customer.getIsactive()==0) {
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
