package myapp.ebank.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


/**
 * The interface Feign police record service.
 */
@FeignClient(name = "e-police-system")
public interface FeignPoliceRecordService {
  /*  @GetMapping("/currency/getByid/{id}")
    public String getByid(@PathVariable int id);
*/
    //public PoliceRecordDTO getPoliceRecord(String cnic);

    /**
     * Check criminal record boolean.
     *
     * @param cnic the cnic
     * @return the boolean
     */
    @GetMapping("/criminal/check-criminal-record")
    public Boolean checkCriminalRecord(@RequestHeader String cnic);
}
