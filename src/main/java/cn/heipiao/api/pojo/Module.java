package cn.heipiao.api.pojo;

public final class Module {
	
    private Integer id;

    private Integer pid;

    private String name;

    private String url;
    
    private String empId;//其他业务字段
    
    public Integer getId() {
		return id;
	}

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", pid=" + pid + ", name=" + name + ", url=" + url + ", empId=" + empId + "]";
	}

}