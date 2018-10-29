package com.onway.fyapp.common.dal.dataobject.returnObj;

/**
 * 
 * @author lishuaikai
 *
 * 2018年7月16日 下午4:01:39
 */
public class VersionInfo {

	private String versionNum;
	
	private String version_type;
	
	private String createTime;
	
	private String downloadUrl;

	private boolean needUpdate;
	
	private String describe;
	
	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public String getVersion_type() {
		return version_type;
	}

	public void setVersion_type(String version_type) {
		this.version_type = version_type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
