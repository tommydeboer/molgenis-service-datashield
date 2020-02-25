package org.molgenis.datashield.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.molgenis.datashield.service.model.PackageTest.BASE;
import static org.molgenis.datashield.service.model.PackageTest.DESC;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.molgenis.datashield.r.REXPParser;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {
  @Mock private REXPParser rexpParser;
  @Mock private REXPString rexp;
  @Mock private RConnection rConnection;

  private PackageService packageService;

  @BeforeEach
  void before() {
    packageService = new PackageService(rexpParser);
  }

  @Test
  public void testGetInstalledPackages() throws REXPMismatchException, RserveException {
    when(rConnection.eval("installed.packages(fields=c())")).thenReturn(rexp);
    when(rexpParser.toStringMap(rexp))
        .thenReturn(
            List.of(
                Map.of(
                    "Package",
                    BASE.name(),
                    "Version",
                    BASE.version(),
                    "Built",
                    BASE.built(),
                    "LibPath",
                    BASE.libPath()),
                Map.of(
                    "Package",
                    DESC.name(),
                    "Version",
                    DESC.version(),
                    "Built",
                    DESC.built(),
                    "LibPath",
                    DESC.libPath())));
    assertEquals(List.of(BASE, DESC), packageService.getInstalledPackages(rConnection));
  }
}
