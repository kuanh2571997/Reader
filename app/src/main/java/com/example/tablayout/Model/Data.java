package com.example.tablayout.Model;

import java.io.Serializable;

public class Data implements Serializable {

    private String linkImg;
    private String tieude;
    private String thoigian;
    private String link;
    private String des;

    public Data() {
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getTieude() {
        String tieudenew = tieude.replace("'","`");
        return tieudenew;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getThoigian() {
//        String time = thoigian.substring(4,16);
//        String year = time.substring(8);
//        String day = time.substring(1,3);
//        String month = time.substring(4,7);
//        String mon ="";
//        if(month.equals("Jan")){
//            mon = "01";
//        }
//        else if(month.equals("Feb")){
//            mon = "02";
//        }
//        else if(month.equals("Mar")){
//            mon = "03";
//        }
//        else if(month.equals("Apr")){
//            mon = "04";
//        }
//        else if(month.equals("May")){
//            mon = "05";
//        }
//        else if(month.equals("Jun")){
//            mon = "06";
//        }
//        else if(month.equals("Jul")){
//            mon = "07";
//        }
//        else if(month.equals("Aug")){
//            mon = "08";
//        }
//        else if(month.equals("Sep")){
//            mon = "09";
//        }
//        else if(month.equals("Oct")){
//            mon = "10";
//        }
//        else if(month.equals("Nov")){
//            mon = "11";
//        }
//        else if(month.equals("Dec")){
//            mon = "12";
//        }
//        return year+"-"+mon+"-"+day;
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
