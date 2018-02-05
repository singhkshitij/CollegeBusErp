package com.edu.aimt;
public class DataObject {
    private String mName,mBranch,mPresent,mAbsent;
    public DataObject (String name, String branch,String present,String absent){
        mName=name;
        mBranch=branch;
        mPresent=present;
        mAbsent=absent;
    }

    public String getName() {
        return mName;
    }
 
    public String getBranch() {
        return mBranch;
    }
    public String getPresent() {
        return mPresent;
    }
    public String getAbsent() {
        return mAbsent;
    }
}