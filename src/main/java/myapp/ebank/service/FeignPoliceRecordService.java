package myapp.ebank.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "e-police-system")
public interface FeignPoliceRecordService {
  /*  @GetMapping("/currency/getByid/{id}")
    public String getByid(@PathVariable int id);

*/
    //public PoliceRecordDTO getPoliceRecord(String cnic);

    /**
     * find criminals by their cnic
     *
     * @param cnic
     * @return
     */
    @GetMapping("/criminal/check-criminal-record")
    public Boolean checkCriminalRecord(@RequestHeader String cnic);
}
