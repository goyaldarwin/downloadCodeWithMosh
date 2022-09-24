package com.darwin.learning.projects.downloadcodewithmosh.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class HtmlUtils {
    private static final String cookies = "aid=483a247a-837f-476a-8edc-2cafaeb90514; ajs_group_id=null; sk_t22ds596_access=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VyIiwiaWF0IjoxNjQxNzM3MDI1LCJqdGkiOiIxY2YwZGI5ZS0wNDM5LTQ0ZTgtYTU1OS00YjAyYmE0NzhlMDMiLCJpc3MiOiJza190MjJkczU5NiIsInN1YiI6IjEzNTBlOWQ5LTZkY2UtNDkwNC05YTNhLTNjYTM0YjI3NjZlNiJ9.lO59s56Rex35Wf1dh3813q2kd_OkC9K2-4p_-KMGHmE; signed_in=true; site_preview=logged_in; ajs_user_id=%2263943840%22; wistiaVisitorKey=4331fdc_f9397f17-12d6-404c-a909-6127a8fb7ceb-839a98df4-f18666a1677f-f09f; ahoy_visitor=1efbdcec-ce7b-4430-a56c-d799787187c0; _afid=483a247a-837f-476a-8edc-2cafaeb90514; _session_id=33e0603859767a158c044e5ac6608a3a; ac_enable_tracking=1; ajs_anonymous_id=%2262655a55-995d-4eec-9a64-b395406e2c65%22; ahoy_visit=a832f004-d763-442e-b17a-53252b2e6fb5; __cfruid=9e0ef682ce8563582e5550190c903c6657262a39-1662346250; ahoy_events=%5B%7B%22id%22%3A%22103dba50-39ad-4a22-a9f3-16e85cf3dbe0%22%2C%22name%22%3A%22%24view%22%2C%22properties%22%3A%7B%22url%22%3A%22https%3A//codewithmosh.com/courses/enrolled/759570%22%2C%22title%22%3A%22Code%20with%20Mosh%22%2C%22page%22%3A%22/courses/enrolled/759570%22%7D%2C%22time%22%3A1662348480.58%7D%5D";

    public static Document getHtmlFromUrl(String url) {
        try {
            return Jsoup.connect(url)
                    .cookies(getFormattedCookies(cookies))
                    .get();
        } catch (Exception e) {
            System.out.format("Exception occurred while fetching html from url: %s", url);
        }
        return null;
    }

    private static Map<String, String> getFormattedCookies(String cookies) {
        Map<String, String> cookiesMap = new HashMap<>();
        String[] keyValues = cookies.split(";");
        for (String keyValue : keyValues) {
            String[] cookie = keyValue.split("=");
            cookiesMap.put(cookie[0], cookie[1]);
        }
        return cookiesMap;
    }
}
