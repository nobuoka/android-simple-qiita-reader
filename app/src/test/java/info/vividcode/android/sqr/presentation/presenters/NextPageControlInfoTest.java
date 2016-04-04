package info.vividcode.android.sqr.presentation.presenters;

import org.junit.Test;

import info.vividcode.android.sqr.presentation.models.LoadingState;
import info.vividcode.android.sqr.presentation.models.NextPageExistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NextPageControlInfoTest {

    @Test
    public void onlyOneMethodReturnsTrue() {
        for (LoadingState ls : LoadingState.values()) {
            for (NextPageExistence npe : NextPageExistence.values()) {
                NextPageControlInfo info = new NextPageControlInfo(ls, npe);
                int trueCount = 0;
                if (info.doShowNothing()) trueCount += 1;
                if (info.doShowErrorMessage()) trueCount += 1;
                if (info.doShowProgress()) trueCount += 1;
                assertEquals("常にどれか 1 つのみ真。 (ls: " + ls + ", npe: " + npe + ")",
                        1, trueCount);
            }
        }
    }

    @Test
    public void doShowErrorMessage() {
        for (LoadingState ls : LoadingState.values()) {
            for (NextPageExistence npe : NextPageExistence.values()) {
                NextPageControlInfo info = new NextPageControlInfo(ls, npe);
                if (ls == LoadingState.ERROR && npe != NextPageExistence.NOT_EXIST) {
                    assertTrue("エラー発生時かつ次ページがある可能性があるときエラー表示。 (" + ls + ", npe: " + npe + ")",
                            info.doShowErrorMessage());
                } else {
                    assertFalse("エラー発生でないならエラー表示はしない。 (" + ls + ", npe: " + npe + ")",
                            info.doShowErrorMessage());
                }
            }
        }
    }

    // TODO : 他のテストも。

}
