package com.moxi.veilletechnoback.Pdf;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfReportOptions {
    private boolean includeAllProjects = true;
    private boolean includeTechnologies = true;
    private boolean includeHistory = false;
    private List<Long> projectIdsToInclude;
    private List<Long> techIdsToInclude;
}
