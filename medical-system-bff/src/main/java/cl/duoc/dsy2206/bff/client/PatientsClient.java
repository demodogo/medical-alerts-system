package cl.duoc.dsy2206.bff.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import cl.duoc.dsy2206.bff.dto.PatientRequest;

@Component
public class PatientsClient {

 private final RestClient restClient;
 private final String baseUrl;

 public PatientsClient(RestClient restClient,
   @Value("${services.patients.baseUrl}") String baseUrl) {
  this.restClient = restClient;
  this.baseUrl = baseUrl;
 }

 public List<Map<String, Object>> findAll() {
  return restClient.get().uri(baseUrl + "/patients").retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> findById(Long id) {
  return restClient.get().uri(baseUrl + "/patients/{id}", id).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> create(PatientRequest request) {
  return restClient.post().uri(baseUrl + "/patients").body(request).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> update(Long id, PatientRequest request) {
  return restClient.put().uri(baseUrl + "/patients/{id}", id).body(request).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public void delete(Long id) {
  restClient.delete().uri(baseUrl + "/patients/{id}", id).retrieve().toBodilessEntity();
 }
}
