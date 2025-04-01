//package com.sammysbrow.backend.controller.appointment;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sammysbrow.backend.dto.appointment.AppointmentDetailsDateTimeDto;
//import com.sammysbrow.backend.dto.appointment.AppointmentDetailsDto;
//import com.sammysbrow.backend.entity.appointment.AppointmentDetails;
//import com.sammysbrow.backend.mapper.appointment.AppointmentDetailsMapper;
//import com.sammysbrow.backend.repository.appointment.AppointmentDetailsRepository;
//import com.sammysbrow.backend.service.appointment.AppointmentDetailsService;
//import com.sammysbrow.backend.service.appointment.projection.AppointmentDetailsDateTimeService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.*;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class AppointmentDetailsControllerTest {
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @Mock
//    private AppointmentDetailsService appointmentDetailsService;
//
//    @Mock
//    private AppointmentDetailsRepository appointmentDetailsRepository;
//
//    @Autowired
//    private MockMvc mvc;
//
//    Long number = 1234567890L;
//
//
////    @Test
////    void shouldAddAppointment() throws Exception {
////
////        final AppointmentDetails newAppointment = new AppointmentDetails(14L, LocalDate.now(), LocalTime.now(),"Eyebrow", "Fname", "email", number,"notes");
////
////        AppointmentDetailsDto dto = AppointmentDetailsMapper.mapToAppointmentDetailsDto(newAppointment);
////        Mockito.when(appointmentDetailsService.addAppointment(Mockito.mock(AppointmentDetailsDto.class))).thenReturn(dto);
////
////        String content = mapper.writeValueAsString(dto);
////
////        RequestBuilder request = MockMvcRequestBuilders
////                .post("/api/appointments")
////                .accept(MediaType.APPLICATION_JSON)
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(content);
////
////        ResultActions result = mvc.perform(request);
////        MvcResult mvcResult = result.andReturn();
////
////        Assertions.assertThat(mvcResult).isNotNull();
////        String userJson = mvcResult.getResponse().getContentAsString();
////        Assertions.assertThat(userJson).isNotEmpty();
////        Assertions.assertThat(userJson).isEqualToIgnoringCase(mapper.writeValueAsString(dto));
////    }
//
//    @Test
//    void shouldGetAllAppointmentDetails() throws Exception {
//        LocalDate date = LocalDate.of(2020, 1, 1);
//        LocalDate date2 = LocalDate.of(2026, 1, 1);
//
//        LocalTime time = LocalTime.of(12, 30);
//        LocalDateTime dt = LocalDateTime.of(date, time);
//        final AppointmentDetails newAppointment = new AppointmentDetails(14L, dt,"Eyebrow", "Fname", "email", number,"notes");
//
//        AppointmentDetailsDto dto = AppointmentDetailsMapper.mapToAppointmentDetailsDto(newAppointment);
//        List<AppointmentDetailsDto> dtoList = new ArrayList<>();
//        dtoList.add(dto);
//
//        Mockito.when(appointmentDetailsService.getAllAppointmentDetails()).thenReturn(dtoList);
//
//        RequestBuilder req = MockMvcRequestBuilders.get("/api/appointments").accept(MediaType.APPLICATION_JSON);
//        MvcResult result = mvc.perform(req).andReturn();
//
//
//        System.out.println("###########");
//        System.out.println(result.getResponse().getContentAsString());
//
////                .andDo(print()).andExpect(status().isOk())
////                .andExpect(jsonPath("$[0].appointmentId").value(8));
//
//
//    }
//
//    @Test
//    void ShouldGetRelevantAppointmentDetails() throws Exception {
//
//
//        RequestBuilder req = MockMvcRequestBuilders.get("/api/appointments/relevant").accept(MediaType.APPLICATION_JSON);
//        MvcResult result = mvc.perform(req).andReturn();
//
//
//        System.out.println("###########");
//        System.out.println(result.getResponse().getContentAsString());
//
//    }
//
//
//
//}
