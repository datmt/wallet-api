package com.datmt.wallet.api.controllers;

import com.datmt.wallet.api.models.FileUpload;
import com.datmt.wallet.api.services.FileUploadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileUploadController.class)
@AutoConfigureMockMvc(addFilters = false)
class FileUploadControllerTest {

    @MockBean
    FileUploadService fileUploadService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test upload file")
    void upload() throws Exception {
        var fileUpload = new FileUpload();
        fileUpload.setS3Path("test.txt");
        fileUpload.setOwnerId("test-owner-id");

        var multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());
        when(fileUploadService.upload(multipartFile)).thenReturn(fileUpload);


        this.mockMvc.perform(multipart("/api/files")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("test-owner-id")));


    }
}