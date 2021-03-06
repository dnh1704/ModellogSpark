package model;

import java.sql.Timestamp;

public class ModelLog {
    private Timestamp timeCreate;
    private int browserCode;
    private String browserVer;
    private String osName;
    private int osCode;
    private String osVer;
    private long ip;
    private String domain;
    private String path;
    private Timestamp cookieCreate;
    private long guid;
    private int siteId;
    private int cId;
    private String referer;
    private int geographic;
    private int locId;
    private String flashVersion;
    private String jre;
    private String sr;
    private String sc;
    private String category;

    public ModelLog(Timestamp timeCreate, int browserCode, String browserVer, String osName,
                    int osCode, String osVer, long ip, String domain, String path,
                    Timestamp cookieCreate, long guid, int siteId, int cId, String referer,
                    int geographic, int locId, String flashVersion, String jre, String sr,
                    String sc, String category) {
        this.timeCreate = timeCreate;
        this.browserCode = browserCode;
        this.browserVer = browserVer;
        this.osName = osName;
        this.osCode = osCode;
        this.osVer = osVer;
        this.ip = ip;
        this.domain = domain;
        this.path = path;
        this.cookieCreate = cookieCreate;
        this.guid = guid;
        this.siteId = siteId;
        this.cId = cId;
        this.referer = referer;
        this.geographic = geographic;
        this.locId = locId;
        this.flashVersion = flashVersion;
        this.jre = jre;
        this.sr = sr;
        this.sc = sc;
        this.category = category;
    }

    public Timestamp getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Timestamp timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Timestamp getCookieCreate() {
        return cookieCreate;
    }

    public void setCookieCreate(Timestamp cookieCreate) {
        this.cookieCreate = cookieCreate;
    }

    public int getBrowserCode() {
        return browserCode;
    }

    public void setBrowserCode(int browserCode) {
        this.browserCode = browserCode;
    }

    public String getBrowserVer() {
        return browserVer;
    }

    public void setBrowserVer(String browserVer) {
        this.browserVer = browserVer;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public int getOsCode() {
        return osCode;
    }

    public void setOsCode(int osCode) {
        this.osCode = osCode;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public long getIp() {
        return ip;
    }

    public void setIp(long ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public int getGeographic() {
        return geographic;
    }

    public void setGeographic(int geographic) {
        this.geographic = geographic;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getFlashVersion() {
        return flashVersion;
    }

    public void setFlashVersion(String flashVersion) {
        this.flashVersion = flashVersion;
    }

    public String getJre() {
        return jre;
    }

    public void setJre(String jre) {
        this.jre = jre;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return timeCreate.toString() + " " + ip + " " + osName;
    }
}