/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-11-08 01:41:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write(' ');

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

      out.write("\r\n");
      out.write("\r\n");
      out.write("  \r\n");
      out.write("           \r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<script id=\"allmobilize\" charset=\"utf-8\" src=\"style/js/allmobilize.min.js\"></script>\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />\r\n");
      out.write("<link rel=\"alternate\" media=\"handheld\"  />\r\n");
      out.write("<!-- end 云适配 -->\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>大学生兼职平台-登录</title>\r\n");
      out.write("<meta property=\"qc:admins\" content=\"23635710066417756375\" />\r\n");
      out.write("<meta content=\"大学生兼职平台-登录\" name=\"description\">\r\n");
      out.write("<meta content=\"大学生兼职平台-登录\" name=\"keywords\">\r\n");
      out.write("\r\n");
      out.write("<meta name=\"baidu-site-verification\" content=\"QIQ6KC1oZ6\" />\r\n");
      out.write("\r\n");
      out.write("<!-- <div class=\"web_root\"  style=\"display:none\">h</div> -->\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var ctx = \"h\";\r\n");
      out.write("console.log(1);\r\n");
      out.write("</script>\r\n");
      out.write("<link rel=\"Shortcut Icon\" href=\"h/images/favicon.ico\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"style/css/style.css\"/>\r\n");
      out.write("\r\n");
      out.write("<script src=\"style/js/jquery.1.10.1.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"style/js/jquery.lib.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"style/js/core.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var youdao_conv_id = 271546;\r\n");
      out.write("</script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"style/js/conv.js\"></script>\r\n");
      out.write("</head>\r\n");
 Cookie c=new Cookie("email","");
           c.setMaxAge(600);
           response.addCookie(c);
           
      out.write("\r\n");
      out.write("<body id=\"login_bg\">\r\n");
      out.write("\t<div class=\"login_wrapper\">\r\n");
      out.write("\t\t<div class=\"login_header\">\r\n");
      out.write("\r\n");
      out.write("            <div id=\"cloud_s\"><img src=\"style/images/cloud_s.png\" width=\"81\" height=\"52\" alt=\"cloud\" /></div>\r\n");
      out.write("            <div id=\"cloud_m\"><img src=\"style/images/cloud_m.png\" width=\"136\" height=\"95\"  alt=\"cloud\" /></div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("    \t<input type=\"hidden\" id=\"resubmitToken\" value=\"\" />\r\n");
      out.write("\t\t <div class=\"login_box\">\r\n");
      out.write("        \t<form id=\"loginForm\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"email\" name=\"email\" value=\"\" tabindex=\"1\" placeholder=\"请输入用户名\" />\r\n");
      out.write("\t\t\t  \t<input type=\"password\" id=\"password\" name=\"password\" tabindex=\"2\" placeholder=\"请输入密码\" />\r\n");
      out.write("\t\t\t\t<span class=\"error\" style=\"display:none;\" id=\"beError\"></span>\r\n");
      out.write("\t\t\t    <label class=\"fl\" for=\"remember\"><input type=\"checkbox\" id=\"remember\" value=\"\" checked=\"checked\" name=\"autoLogin\" /> 记住我</label>\r\n");
      out.write("\t\t\t    <a href=\"reset\" class=\"fr\" target=\"_blank\">忘记密码？</a>\r\n");
      out.write("\t\t\t\t<input type=\"submit\" id=\"submitLogin\" value=\"登 &nbsp; &nbsp; 录\" />\r\n");
      out.write("\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t\t<div class=\"login_right\">\r\n");
      out.write("\t\t\t<br/>\r\n");
      out.write("\t\t\t<br/>\r\n");
      out.write("\t\t\t\t<div>还没有帐号？</div>\r\n");
      out.write("\t\t\t\t<a  href=\"register\"  class=\"registor_now\">立即注册</a>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"login_box_btm\"></div>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("$(function(){\r\n");
      out.write("\t//验证表单\r\n");
      out.write("\t \t$(\"#loginForm\").validate({\r\n");
      out.write("\t \t\t/* onkeyup: false,\r\n");
      out.write("\t    \tfocusCleanup:true, */\r\n");
      out.write("\t        rules: {\r\n");
      out.write("\t    \t   \temail: {\r\n");
      out.write("\t    \t    \trequired: true\r\n");
      out.write("\t    \t   \t},\r\n");
      out.write("\t    \t   \tpassword: {\r\n");
      out.write("\t    \t    \trequired: true\r\n");
      out.write("\t    \t   \t}\r\n");
      out.write("\t    \t},\r\n");
      out.write("\t    \tmessages: {\r\n");
      out.write("\t    \t   \temail: {\r\n");
      out.write("\t    \t    \trequired: \"请输入用户名\"\r\n");
      out.write("\t    \t   \t},\r\n");
      out.write("\t    \t   \tpassword: {\r\n");
      out.write("\t    \t    \trequired: \"请输入密码\"\r\n");
      out.write("\t    \t   \t}\r\n");
      out.write("\t    \t},\r\n");
      out.write("\t    \tsubmitHandler:function(form){\r\n");
      out.write("\t    \t\tif($('#remember').prop(\"checked\")){\r\n");
      out.write("\t      \t\t\t$('#remember').val(1);\r\n");
      out.write("\t      \t\t}else{\r\n");
      out.write("\t      \t\t\t$('#remember').val(null);\r\n");
      out.write("\t      \t\t}\r\n");
      out.write("\t    \t\tvar email = $('#email').val();\r\n");
      out.write("\t    \t\tvar password = $('#password').val();\r\n");
      out.write("\t    \t\tvar remember = $('#remember').val();\r\n");
      out.write("\r\n");
      out.write("\t    \t\t$(form).find(\":submit\").attr(\"disabled\", true);\r\n");
      out.write("\t            $.ajax({\r\n");
      out.write("\t            \ttype:'POST',\r\n");
      out.write("\t            \tdata:{\"geNumber\":email,\"password\":password},\r\n");
      out.write("\t            \turl:'UserType.do',\r\n");
      out.write("\t\t\t\t\tasync:false,\r\n");
      out.write("\t\t\t\t\tdataType: 'json',\r\n");
      out.write("\t            }).done(function(result) {\r\n");
      out.write("\t                if(result.tip==\"error\"){\r\n");
      out.write("\t                        window.location='error';\r\n");
      out.write("\t                }else if(result.tip==\"success\"){\r\n");
      out.write("                     \twindow.location='maSystem';\r\n");
      out.write("                    }\r\n");
      out.write("\t\t\t\t\t$(form).find(\":submit\").attr(\"disabled\", false);\r\n");
      out.write("\t            });\r\n");
      out.write("\t        }\r\n");
      out.write("\t\t});\r\n");
      out.write("})\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
