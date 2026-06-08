package cl.duoc.dsy2206.bff.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import cl.duoc.dsy2206.bff.dto.AlertRequest;
import cl.duoc.dsy2206.bff.dto.VitalSignRequest;

@Component
public class ClinicalClient {

 private final RestClient restClient;
 private final String baseUrl;

 public ClinicalClient(RestClient restClient,
   @Value("${services.clinical.baseUrl}") String baseUrl) {
  this.restClient = restClient;
  this.baseUrl = baseUrl;
 }

 public List<Map<String, Object>> findAllVitalSigns() {
  return restClient.get().uri(baseUrl + "/vital-signs").retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> findVitalSignById(Long id) {
  return restClient.get().uri(baseUrl + "/vital-signs/{id}", id).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public List<Map<String, Object>> findVitalSignsByPatientId(Long patientId) {
  return restClient.get().uri(baseUrl + "/vital-signs/patient/{patientId}", patientId).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> createVitalSign(VitalSignRequest request) {
  return restClient.post().uri(baseUrl + "/vital-signs").body(request).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> updateVitalSign(Long id, VitalSignRequest request) {
  return restClient.put().uri(baseUrl + "/vital-signs/{id}", id).body(request).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public void deleteVitalSign(Long id) {
  restClient.delete().uri(baseUrl + "/vital-signs/{id}", id).retrieve().toBodilessEntity();
 }

 public List<Map<String, Object>> findAllAlerts() {
  return restClient.get().uri(baseUrl + "/alerts").retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> findAlertById(Long id) {
  return restClient.get().uri(baseUrl + "/alerts/{id}", id).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public List<Map<String, Object>> findAlertsByPatientId(Long patientId) {
  return restClient.get().uri(baseUrl + "/alerts/patient/{patientId}", patientId).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> createAlert(AlertRequest request) {
  return restClient.post().uri(baseUrl + "/alerts").body(request).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public Map<String, Object> updateAlert(Long id, AlertRequest request) {
  return restClient.put().uri(baseUrl + "/alerts/{id}", id).body(request).retrieve()
    .body(new ParameterizedTypeReference<>() {});
 }

 public void deleteAlert(Long id) {
  restClient.delete().uri(baseUrl + "/alerts/{id}", id).retrieve().toBodilessEntity();
 }
}
