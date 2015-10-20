package action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import resmodel.ModelAppUpdate;
import resmodel.ResUpdate;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class CheckVersion implements Action
{
	public String jsonstr;

	@Override
	public String execute() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		String versioncode = (String)ctx.getApplication().get("version");
		String versionname = (String)ctx.getApplication().get("versionname");
//		String updateinfo = new String(toByteArray(ctx.getApplication().get("updateinfo")),"utf-8");
		String updateinfo = (String)ctx.getApplication().get("updateinfo");
		String downloadurl = (String)ctx.getApplication().get("downloadurl");
		
		ModelAppUpdate model = new ModelAppUpdate();
		model.setVersioncode(Integer.parseInt(versioncode));
		model.setVersionname(versionname);
		model.setUpdateinfo(updateinfo);
		model.setDownloadurl(downloadurl);
		ResUpdate res = new ResUpdate();
		res.setStat(true);
		res.setVersion(model);
		
		this.jsonstr = new Gson().toJson(res);
		
		return SUCCESS;
	}
	
	public byte[] toByteArray (Object obj) {      
        byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
        return bytes;    
    }
	
}