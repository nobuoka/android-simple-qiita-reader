package info.vividcode.android.sqr.infrastructure.webapi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.vividcode.android.sqr.dto.Tag;
import info.vividcode.android.sqr.dto.QiitaItem;
import info.vividcode.android.sqr.dto.User;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import rx.observers.TestSubscriber;

import static java.util.Collections.singletonList;

public class WebApiQiitaItemListLoaderTest {

    private MockWebServer mMockWebServer;

    private WebApiQiitaItemListLoader mTestTargetLoader;

    @Before
    public void setup() throws IOException {
        mMockWebServer = new MockWebServer();
        mMockWebServer.start();

        OkHttpClient client = new OkHttpClient.Builder().build();
        mTestTargetLoader = new WebApiQiitaItemListLoader(
                QiitaServiceFactory.INSTANCE.createQiitaService(client, mMockWebServer.url("").toString()));
    }

    @After
    public void teardown() throws IOException {
        mMockWebServer.shutdown();
    }

    @Test
    public void getQiitaItemList_emptyArrayResponse() {
        mMockWebServer.enqueue(new MockResponse().setBody("[]"));

        TestSubscriber<List<QiitaItem>> testSubscriber = new TestSubscriber<>();
        mTestTargetLoader.getQiitaItemList().subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(5, TimeUnit.SECONDS);
        testSubscriber.assertTerminalEvent();
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(singletonList(Collections.<QiitaItem>emptyList()));
    }

    @Test
    public void getQiitaItemList_twoItemsArrayResponse() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                this.getClass().getClassLoader().getResourceAsStream("web_api_qiita_items_two_items.json"),
                StandardCharsets.UTF_8))) {
            String line;
            while (null != (line = br.readLine())) sb.append(line);
        }
        mMockWebServer.enqueue(new MockResponse().setBody(sb.toString()));

        TestSubscriber<List<QiitaItem>> testSubscriber = new TestSubscriber<>();
        mTestTargetLoader.getQiitaItemList().subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(5, TimeUnit.SECONDS);
        testSubscriber.assertTerminalEvent();
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(singletonList(Arrays.asList(
                new QiitaItem(
                        "9d9f9a2f78b013e7dd14",
                        "Electron で何かを作る",
                        "# 準備",
                        "<h1>準備</h1>",
                        false,
                        "2016-04-03T12:34:56+09:00",
                        "2016-04-03T12:34:56+09:00",
                        false,
                        "http://example.com/test-url-1",
                        singletonList(new Tag("Electron", Collections.<String>emptyList())),
                        new User(
                                "test-taro",
                                "テスト 太郎",
                                "http://example.com/profile-image",
                                "",
                                ""
                        )
                ),
                new QiitaItem(
                        "9d9f9a2f78b013e7dd15",
                        "Android で何かをする",
                        "# 何かをする",
                        "<h1>何かをする</h1>",
                        false,
                        "2016-04-03T10:00:00+09:00",
                        "2016-04-03T10:00:00+09:00",
                        false,
                        "http://example.com/test-url-2",
                        singletonList(new Tag("Android", Collections.<String>emptyList())),
                        new User(
                                "test-taro",
                                "テスト 太郎",
                                "http://example.com/profile-image",
                                "",
                                ""
                        )
                )
        )));
    }

    @Test
    public void getQiitaItemList_errorResponse() {
        mMockWebServer.enqueue(new MockResponse().setResponseCode(500).setBody("Internal Error"));

        TestSubscriber<List<QiitaItem>> testSubscriber = new TestSubscriber<>();
        mTestTargetLoader.getQiitaItemList().subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(5, TimeUnit.SECONDS);
        testSubscriber.assertTerminalEvent();
        testSubscriber.assertNoValues();
        testSubscriber.assertError(RuntimeException.class);
    }

}
