package hzm.table.analysis.util.universal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExeclUtil {
	/**
	 * Author: Date: Description:
	 */
	public static class ExcelReader {
		// 日志打印类
		// private static Logger logger = Logger.getLogger(ExcelReader.class.getName());

		private static final String XLS = "xls";
		private static final String XLSX = "xlsx";

		/**
		 * 根据文件后缀名类型获取对应的工作簿对象
		 * 
		 * @param inputStream 读取文件的输入流
		 * @param fileType    文件后缀名类型（xls或xlsx）
		 * @return 包含文件数据的工作簿对象
		 * @throws IOException
		 */
		public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase(XLS)) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (fileType.equalsIgnoreCase(XLSX)) {
				workbook = new XSSFWorkbook(inputStream);
			}
			return workbook;
		}

		/**
		 * 读取Excel文件内容
		 * 
		 * @param fileName 要读取的Excel文件所在路径
		 * @return 读取结果列表，读取失败时返回null
		 */
		public static List<Map<String, String>> readExcel(String fileName) {
			
			Workbook workbook = null;
			FileInputStream inputStream = null;
			try {
				// 获取Excel后缀名
	            if (fileName == null || fileName.isEmpty() || fileName.lastIndexOf(".") < 0) {
	                //logger.warning("解析Excel失败，因为获取到的Excel文件名非法！");
	            	System.out.println("解析Excel失败，因为获取到的Excel文件名非法！");
	                return null;
	            }
				String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				// 获取Excel文件
				File excelFile = new File(fileName);
				if (!excelFile.exists()) {
					// logger.warning("指定的Excel文件不存在！");
					System.out.println("指定的Excel文件不存在！");
					return null;
				}
				// 获取Excel工作簿
				inputStream = new FileInputStream(excelFile);
				workbook = getWorkbook(inputStream, fileType);
				// 读取excel中的数据
				List<Map<String, String>> resultDataList = parseExcel(workbook);
				return resultDataList;
			} catch (Exception e) {
				// logger.warning("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
				System.out.println("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
				return null;
			} finally {
				try {
					if (workbook != null ) {
						workbook.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e) {
					// logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
					System.out.println("关闭数据流出错！错误信息：" + e.getMessage());
					return null;
				}
			}
			
		}

		/**
		 * 解析Excel数据
		 * 
		 * @param workbook Excel工作簿对象
		 * @return 解析结果
		 */
		private static List<Map<String, String>> parseExcel(Workbook workbook) {
			List<Map<String, String>> resultDataList = new ArrayList<>();
			//解析sheet
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				Sheet sheet = workbook.getSheetAt(sheetNum);
				// 校验sheet是否合法
				if (sheet == null) {
					continue;
				}
				// 获取第一行数据
				int firstRowNum = sheet.getFirstRowNum();
				Row firstRow = sheet.getRow(firstRowNum);
				if (firstRow == null) {
					// logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
					System.out.println("解析Excel失败，在第一行没有读取到任何数据！");
				}
				// 解析每一行的数据，构造数据对象
				int rowStart = firstRowNum + 1;
				int rowEnd = sheet.getPhysicalNumberOfRows();
				for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
					Row row = sheet.getRow(rowNum);

					if (null == row) {
						continue;
					}

					Map<String, String> resultData = convertRowToData(row);
					if (null == resultData) {
						// logger.warning("第 " + row.getRowNum() + "行数据不合法，已忽略！");
						continue;
					}
					resultDataList.add(resultData);
				}
			}

			return resultDataList;
		}

		/**
		 * 将单元格内容转换为字符串
		 * 
		 * @param cell
		 * @return
		 */
		private static String convertCellValueToString(Cell cell) {
			if (cell == null) {
				return null;
			}
			String returnValue = null;
			switch (cell.getCellType()) {
			case NUMERIC: // 数字
				Double doubleValue = cell.getNumericCellValue();
				// 格式化科学计数法，取一位整数
				DecimalFormat df = new DecimalFormat("0");
				returnValue = df.format(doubleValue);
				break;
			case STRING: // 字符串
				returnValue = cell.getStringCellValue();
				break;
			case BOOLEAN: // 布尔
				Boolean booleanValue = cell.getBooleanCellValue();
				returnValue = booleanValue.toString();
				break;
			case BLANK: // 空值
				break;
			case FORMULA: // 公式
				returnValue = cell.getCellFormula();
				break;
			case ERROR: // 故障
				break;
			default:
				break;
			}
			return returnValue;
		}

		/**
		 * 提取每一行中需要的数据，构造成为一个结果数据对象
		 *
		 * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
		 *
		 * @param row 行数据
		 * @return 解析后的行数据对象，行数据错误时返回null
		 */
		private static Map<String, String> convertRowToData(Row row) {
			Map<String, String> resultData = new HashMap<>();
			Cell cell;
			int cellNum = 0;
			// 获取姓名
			cell = row.getCell(cellNum++);
			String name = convertCellValueToString(cell);
			resultData.put("name", name);
			// 获取年龄
			cell = row.getCell(cellNum++);
			String ageStr = convertCellValueToString(cell);
			if (null == ageStr || "".equals(ageStr)) {
				// 年龄为空
				resultData.put("age", null);
			} else {
				// resultData.setAge(Integer.parseInt(ageStr));
			}
			// 获取居住地
			cell = row.getCell(cellNum++);
			String location = convertCellValueToString(cell);
			// resultData.setLocation(location);
			// 获取职业
			cell = row.getCell(cellNum++);
			String job = convertCellValueToString(cell);
			// resultData.setJob(job);
			return resultData;
		}
	}

	public static class ExcelWriter {

		private static List<String> CELL_HEADS; // 列头

		static {
			// 类装载时就载入指定好的列头信息，如有需要，可以考虑做成动态生成的列头
			CELL_HEADS = new ArrayList<>();
			CELL_HEADS.add("姓名");
			CELL_HEADS.add("年龄");
			CELL_HEADS.add("居住城市");
			CELL_HEADS.add("职业");
		}

		/**
		 * 生成Excel并写入数据信息
		 * 
		 * @param dataList 数据列表
		 * @return 写入数据后的工作簿对象
		 */
		public static Workbook exportData(List<Map<String, String>> dataList) {
			// 生成xlsx的Excel
			Workbook workbook = new SXSSFWorkbook();

			// 如需生成xls的Excel，请使用下面的工作簿对象，注意后续输出时文件后缀名也需更改为xls
			// Workbook workbook = new HSSFWorkbook();

			// 生成Sheet表，写入第一行的列头
			Sheet sheet = buildDataSheet(workbook);
			// 构建每行的数据内容
			int rowNum = 1;
			// for (Iterator<Map<String, String>> it = dataList.iterator(); it.hasNext(); )
			// {
			// Map<String, String> data = it.next();
			// if (data == null) {
			// continue;
			// }
			// 输出行数据
			// Row row = sheet.createRow(rowNum++);
			// convertDataToRow(data, row);
			// }
			return workbook;
		}

		/**
		 * 生成sheet表，并写入第一行数据（列头）
		 * 
		 * @param workbook 工作簿对象
		 * @return 已经写入列头的Sheet
		 */
		private static Sheet buildDataSheet(Workbook workbook) {
			Sheet sheet = workbook.createSheet();
			// 设置列头宽度
			for (int i = 0; i < CELL_HEADS.size(); i++) {
				sheet.setColumnWidth(i, 4000);
			}
			// 设置默认行高
			sheet.setDefaultRowHeight((short) 400);
			// 构建头单元格样式
			CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
			// 写入第一行各列的数据
			Row head = sheet.createRow(0);
			for (int i = 0; i < CELL_HEADS.size(); i++) {
				Cell cell = head.createCell(i);
				cell.setCellValue(CELL_HEADS.get(i));
				cell.setCellStyle(cellStyle);
			}
			return sheet;
		}

		/**
		 * 设置第一行列头的样式
		 * 
		 * @param workbook 工作簿对象
		 * @return 单元格样式对象
		 */
		private static CellStyle buildHeadCellStyle(Workbook workbook) {
			CellStyle style = workbook.createCellStyle();
			// 对齐方式设置
			style.setAlignment(HorizontalAlignment.CENTER);
			// 边框颜色和宽度设置
			style.setBorderBottom(BorderStyle.THIN);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
			style.setBorderLeft(BorderStyle.THIN);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
			style.setBorderRight(BorderStyle.THIN);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
			style.setBorderTop(BorderStyle.THIN);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
			// 设置背景颜色
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// 粗体字设置
			Font font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);
			return style;
		}

		/**
		 * 将数据转换成行
		 * 
		 * @param data 源数据
		 * @param row  行对象
		 * @return
		 */
		private static void convertDataToRow(Map<String, String> data, Row row) {
			int cellNum = 0;
			Cell cell;
			// 姓名
			cell = row.createCell(cellNum++);
			// cell.setCellValue(null == data.getName() ? "" : data.getName());
			// 年龄
			// cell = row.createCell(cellNum++);
			/// if (null != data.getAge()) {
			// cell.setCellValue(data.getAge());
			// } else {
			// cell.setCellValue("");
			// }
			// 所在城市
			cell = row.createCell(cellNum++);
			// cell.setCellValue(null == data.getLocation() ? "" : data.getLocation());
			// 职业
			cell = row.createCell(cellNum++);
			// cell.setCellValue(null == data.getJob() ? "" : data.getJob());
		}
	}

}
