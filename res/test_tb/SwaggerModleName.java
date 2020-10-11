@ApiModel(description = "")
public class SwaggerModleName {
@ApiModelProperty(value:"代號",required : true,example:"1")
private int id;
@ApiModelProperty(value:"數字",required : true,example:"1")
private int number;
@ApiModelProperty(value:"名字",example:"黃梓閔")
private char name;
@ApiModelProperty(value:"號碼",required : true,example:"123")
private String grade;
}
