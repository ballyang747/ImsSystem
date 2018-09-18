package org.fkjava.ims.common.pager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PageApp extends TagSupport {

    //代表当前页码
	private int pageIndex;
	//每页的显示数
	private int pageSize;
	//总记录数
	private int totalNum;
	//提交的地址
	private String submitUrl; //eg: index.action?pageIndex=??
	//分页的样式
	private String pageStyle = "black";
	
	
	
	@Override
	public int doStartTag() throws JspException {
		System.out.println("-------进入方法--------");
		try {
			System.out.println(pageSize);
			//通过jspwriter写数据
			JspWriter jspWriter = this.pageContext.getOut();
			//通过StringBuffer拼接页面相关信息
			StringBuffer str = new StringBuffer();
			//计算总页数
			int totalPageNum = totalNum % pageSize == 0?totalNum / pageSize : (totalNum / pageSize) + 1;		    //定义跳转的地址
		 	String jumpUrl = "";
		 	//用于拼装页码的信息
		 	StringBuffer page = new StringBuffer();
		 	//以下有三种的情况
		 	//1 第一页 上一页不能有a标签
		 	//2 最后以一个 下一页不能有a标签
		 	//中间页数 
		 	if(pageIndex == 1) {
		 		page.append("<span class='disabled'>上一页</span>");
		 		//处理中间页
		 		calMidderPage(page,totalPageNum);
		 		//如果总共只有一页,那么下一页也是不能点击了
		 		if(totalPageNum == 1) {
		 			page.append("<span class='disabled'>下一页</span>");
		 		}else {
		 			jumpUrl=this.submitUrl.replace("{0}", String.valueOf(this.pageIndex+1));
		 			page.append("<a href='"+jumpUrl+"'>下一页</a>");
		 		}
		 	}else if(pageIndex == totalPageNum) {
               //当前页码是尾页
	 			jumpUrl=this.submitUrl.replace("{0}", String.valueOf(this.pageIndex-1));
	 			page.append("<a href='"+jumpUrl+"'>上一页</a>");
	 			//处理中间页
	 			calMidderPage(page,totalPageNum);
	 			page.append("<span class='disabled'>上一页</span>");
		 	}else {
		 		jumpUrl=this.submitUrl.replace("{0}", String.valueOf(this.pageIndex-1));
	 			page.append("<a href='"+jumpUrl+"'>上一页</a>");
	 			
	 			//处理中间页码   1 2 3 4 5 6 7 8 9 ... 100
	 			calMidderPage(page,totalPageNum);
		 		jumpUrl=this.submitUrl.replace("{0}", String.valueOf(this.pageIndex+1));
	 			page.append("<a href='"+jumpUrl+"'>下一页</a>");			
		 	}
		 	//计算每页的开始以及结束行号 <当前显示?-? 条记录>
		 	int startSize = (this.pageIndex-1)*this.pageSize+1;
		 	int endSize = this.pageIndex == totalPageNum ?  this.totalNum : this.pageIndex * this.pageSize;
		 	str.append("<table class='"+this.pageStyle+"'style='align:center' width='100%'><tr><td>"+page.toString()+" 转跳到<input type='text' size='4' id='jumpNum'/><input type='button' value='跳转' id= 'jumpBut'/></td></tr>");
		 	str.append("<tr><td>总共<font color='red'>"+this.totalNum+"</font>条记录,当前显示"+startSize+"-"+endSize+"条记录</td></tr></table>");
			str.append("<script type='text/javascript'>");
			str.append("document.getElementById('jumpBut').onclick = function(){");
			str.append("var value = document.getElementById('jumpNum').value;");
			str.append("if(!/^[1-9]\\d*$/.test(value)||value > "+this.totalNum+"){");
			str.append("alert('请输入[1-"+totalPageNum+"]之间的页码值！');");
			str.append("}else{");
			// index.action?pageIndex = {0}
			str.append("var submiturl = '"+this.submitUrl+"';");
			str.append("submiturl = submiturl.replace('{0}',value);");
			str.append("window.location = submiturl;");
			
			str.append("}");
			
			str.append("}");
			str.append("</script>");
		 	
			jspWriter.write(str.toString());
		 	
		} catch (Exception e) {
		e.printStackTrace();
		}

		return super.doStartTag();
	}
	private void calMidderPage(StringBuffer page, int totalPageNum) {
		String jumpUrl="";
		//如果中也吗数小于等于10,不要...
		if(totalPageNum <=10) {
			for(int i =1; i<= totalPageNum; i++) {
				//当前页面不需要跳转
				if(i==this.pageIndex) {
					page.append("<span class='current'>"+i+"</span>");
				}else {
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
					page.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
		}else if(this.pageIndex <= 8) {
			//当前页码靠近首页 写死问题不大
			for(int i= 1; i<=9 ; i++) {
				if(i==this.pageIndex) {
					page.append("<span class='current'>"+i+"</span>");
				}else {
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
					page.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
			page.append("...");
			jumpUrl = this.submitUrl.replace("{0}", String.valueOf(totalPageNum));
			page.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
		}else if(this.pageIndex + 8 >= totalPageNum) {
			  //当前页码靠近尾页  1 ... 91 92 93 94 95 96 97 98 99 100 
			jumpUrl = this.submitUrl.replace("{0}", String.valueOf(1));
			page.append("<a href='"+jumpUrl+"'>"+1+"</a>");
			 //拼装...
			page.append("...");
			
			for(int i = totalPageNum-9; i<= totalPageNum; i++) {
				if(i==this.pageIndex) {
					page.append("<span class='current'>"+i+"</span>");
				}else {
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
					page.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
			
		}else {
			//当前页码在中间  1 ... 44 45 46 47 48 49 50 51 52  ... 100
			jumpUrl = this.submitUrl.replace("{0}", String.valueOf(1));
			page.append("<a href='"+jumpUrl+"'>"+1+"</a>");
			 //拼装...
			page.append("...");
			
			for(int i =this.pageIndex-4; i<= this.pageIndex+4; i++) {
				if(i==this.pageIndex) {
					page.append("<span class='current'>"+i+"</span>");
				}else {
					jumpUrl = this.submitUrl.replace("{0}", String.valueOf(i));
					page.append("<a href='"+jumpUrl+"'>"+i+"</a>");
				}
			}
			
			page.append("...");
			jumpUrl = this.submitUrl.replace("{0}", String.valueOf(totalPageNum));
			page.append("<a href='"+jumpUrl+"'>"+totalPageNum+"</a>");
		}
		
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		System.out.println(pageSize);
		this.pageSize = pageSize;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public String getSubmitUrl() {
		return submitUrl;
	}
	public void setSubmitUrl(String submitUrl) {
		System.out.println(submitUrl);
		this.submitUrl = submitUrl;
	}
	public String getPageStyle() {
		return pageStyle;
	}
	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}
	
	
	

}
