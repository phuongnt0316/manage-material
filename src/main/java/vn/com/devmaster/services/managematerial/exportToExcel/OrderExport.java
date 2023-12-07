package vn.com.devmaster.services.managematerial.exportToExcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import vn.com.devmaster.services.managematerial.projection.IOrderInFor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class OrderExport {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<IOrderInFor> orders;

    public OrderExport(List<IOrderInFor> orders) {
        this.orders = orders;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("product");
    }

    public void writeHeaderRow() {
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");

        cell = row.createCell(1);
        cell.setCellValue("Tên người nhận");

        cell = row.createCell(2);
        cell.setCellValue("Số điện thoại");

        cell = row.createCell(3);
        cell.setCellValue("Đại chỉ nhận");

        cell = row.createCell(4);
        cell.setCellValue("Ghi chú");

        cell = row.createCell(5);
        cell.setCellValue("Ngày đặt");

        cell = row.createCell(6);
        cell.setCellValue("Phương thức thanh toán");

        cell = row.createCell(7);
        cell.setCellValue("Phương thức vận chuyển");

        cell = row.createCell(8);
        cell.setCellValue("Tổng tiền");

        cell = row.createCell(9);
        cell.setCellValue("Trạng thái");

    }

    public void writeDataRows() {
        int rowCount = 1;
        for (IOrderInFor order : orders) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(order.getId());

            cell = row.createCell(1);
            cell.setCellValue(order.getNameReciver());

            cell = row.createCell(2);
            cell.setCellValue(order.getPhone());

            cell = row.createCell(3);
            cell.setCellValue(order.getAddress());

            cell = row.createCell(4);
            cell.setCellValue(order.getNote());

            cell = row.createCell(5);
            cell.setCellValue(order.getDate());

            cell = row.createCell(6);
            cell.setCellValue(order.getNameTranSport());

            cell = row.createCell(7);
            cell.setCellValue(order.getNamePayment());

            cell = row.createCell(8);
            cell.setCellValue(order.getTotalMoney());

            cell = row.createCell(9);
            switch (order.getStatus()) {
                case 1:
                    cell.setCellValue("Đã đặt hàng");
                    break;
                case 2:
                    cell.setCellValue("Chuẩn bị hàng");
                    break;
                case 3:
                    cell.setCellValue("Đang giao hàng");
                    break;

                case 4:
                    cell.setCellValue("Giao thành công");
                    break;

                case 5:
                    cell.setCellValue("Đã hủy");
                    break;
            }
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
