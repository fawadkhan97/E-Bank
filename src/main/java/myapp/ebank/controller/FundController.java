package myapp.ebank.controller;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.service.FundService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/fund")
@Validated
public class FundController {

    final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }


    /**
     * @return list of funds
     * @Author "Fawad khan"
     * @Description "Display all funds from db in a list if present which can be then
     * displayed on screen"
     * @createdDate 27-oct-2021
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllFunds(HttpServletRequest httpServletRequest) {

        return fundService.listAllFunds(httpServletRequest);

    }


    /**
     * @param fund
     * @return
     * @createdDate 29-oct-2021
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateFund(
            @Valid @RequestBody Funds fund, HttpServletRequest httpServletRequest) {

        return fundService.updateFund(fund,httpServletRequest);

    }

    /**
     * @param id
     * @return
     * @createdDate 27-oct-2021
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteFund(
            @PathVariable Long id, HttpServletRequest httpServletRequest) {
        return fundService.deleteFund(id,httpServletRequest);
    }


}
