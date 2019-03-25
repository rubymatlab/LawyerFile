package test;

import org.jeecgframework.codegenerate.window.CodeWindow;

import com.aspose.words.Document;
import com.lyf.utils.LicenseUtil;

import java.sql.SQLOutput;


/**
 * 【单表模型】代码生成器入口
 * @author 张代浩
 * @site www.jeecg.org
 * 测试word文档输入域
 */
public class JeecgOneGUI {

	public static void main(String[] args) {
		// 验证License
        if (!LicenseUtil.getLicense()) {
            return;
        }
        
		String template="D:\\LawyerFile\\LawyerFile\\MainPorgram\\lawyerfile-master\\src\\main\\webapp\\upload\\files\\工程合同模板.doc";
		//目标word
        String destdoc = "D:\\LawyerFile\\LawyerFile\\MainPorgram\\lawyerfile-master\\src\\main\\webapp\\upload\\files\\edit.docx";
        //定义文档接口
        Document doc;
		try {
			doc = new Document(template); //文本域
	        String[] Flds = new String[]{"委托人","受托人","被上诉人","上诉人","受理机关"};
	        //值
	        Object[] Vals = new Object[]{"职业技术学院","商贸公司","周先生","陈先生","人民法院"};
	        
	        for(String fieldname:doc.getMailMerge().getFieldNames())
	        	System.out.println(fieldname);
	        
	        //调用接口
	        doc.getMailMerge().execute(Flds, Vals);
	        doc.save(destdoc);
	        System.out.println("完成");
	        
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		/*
		CodeWindow  codeWindow = new CodeWindow();
		codeWindow.pack();
		*/
	}
}
