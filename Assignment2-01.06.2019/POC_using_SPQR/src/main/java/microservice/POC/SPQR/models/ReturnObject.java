package microservice.POC.SPQR.models;

import lombok.ToString;

import java.util.List;

@ToString
public class ReturnObject {
    private Integer noOfEntries;
    private List<UserElastic> listUsers;
    private Integer pageIndex;

    public ReturnObject() {
    }

    public ReturnObject(Integer noOfEntries, List<UserElastic> listUsers, Integer pageIndex) {
        this.noOfEntries = noOfEntries;
        this.listUsers = listUsers;
        this.pageIndex = pageIndex;
    }

    public List<UserElastic> getListUsers() {
        return listUsers;
    }

    public Integer getNoOfEntries() {
        return noOfEntries;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setListUsers(List<UserElastic> listUsers) {
        this.listUsers = listUsers;
    }

    public void setNoOfEntries(Integer noOfEntries) {
        this.noOfEntries = noOfEntries;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
}
