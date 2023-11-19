package edu.project3;

import edu.project3.report_creators.AdocReportCreator;
import edu.project3.report_creators.MarkdownReportCreator;
import edu.project3.report_creators.ReportCreator;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportCreatorTest {

    @Test
    void shouldReturnMarkdownReportCreator() {
        ReportCreator reportCreator = ReportCreator.getInstance("markdown");
        assertThat(reportCreator).isInstanceOf(MarkdownReportCreator.class);
    }

    @Test
    void shouldReturnAdocReportCreator() {
        ReportCreator reportCreator = ReportCreator.getInstance("adoc");
        assertThat(reportCreator).isInstanceOf(AdocReportCreator.class);
    }

    @Test
    void shouldReturnNull() {
        ReportCreator reportCreator = ReportCreator.getInstance("ElseCreator");
        assertThat(reportCreator).isNull();
    }
}
