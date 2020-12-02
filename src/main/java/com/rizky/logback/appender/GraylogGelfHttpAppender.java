package com.rizky.logback.appender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class GraylogGelfHttpAppender extends AppenderBase<ILoggingEvent>{

    private String graylog_http_url,hostname="localhost",gelf_version="1.1";
    private boolean verify_ssl=true;
    
    

    public GraylogGelfHttpAppender() {
        Unirest.config().socketTimeout(5000).connectTimeout(5000);
    }

    
    
    public boolean isVerify_ssl() {
        return verify_ssl;
    }



    public void setVerify_ssl(boolean verify_ssl) {
        this.verify_ssl = verify_ssl;
        Unirest.config().verifySsl(verify_ssl);
    }



    public String getGelf_version() {
        return gelf_version;
    }


    public void setGelf_version(String gelf_version) {
        this.gelf_version = gelf_version;
    }


    public String getGraylog_http_url() {
        return graylog_http_url;
    }
    public void setGraylog_http_url(String graylog_http_url) {
        this.graylog_http_url = graylog_http_url;
    }
    public String getHostname() {
        return hostname;
    }
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }


    @Override
    protected void append(ILoggingEvent event) {

        if(graylog_http_url==null)  {
            addError("Graylog GELF Http Address cannot be null" );
        }
        try {
            //Prepare date with ISO 8601 format
            DateFormat df =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String dateISO=df.format(new Date(event.getTimeStamp()));

            //Prepare JSON for GELF
            JSONObject gelf=new JSONObject();
            gelf.put("version", gelf_version);
            gelf.put("host", this.hostname);
            gelf.put("timestamp", dateISO);
            gelf.put("level", convertToSyslogLevel(event.getLevel().toString().toUpperCase()));
            gelf.put("short_message", event.getLoggerName()+" "+event.getFormattedMessage());

            Unirest.post(graylog_http_url)
            .header("Content-Type", "application/json")
            .body(gelf.toString()).asEmpty();


        }
        catch(Exception e)  {
            addError("Failed adding log to GrayLog");
        }

    }


    private int convertToSyslogLevel(String levelName)  {
        //Convert LogBack Log Level to Syslog Level

        if(levelName.contains("DEBUG")||levelName.contains("TRACE"))    {
            return 7;
        }
        else if(levelName.contains("INFO")) {
            return 6;
        }
        else if(levelName.contains("WARN")) {
            return 4;
        }
        else if(levelName.contains("ERROR"))    {
            return 3;
        }
        else    {
            return 5;
        }

    }



}
