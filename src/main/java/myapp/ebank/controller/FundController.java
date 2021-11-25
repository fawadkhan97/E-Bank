package myapp.ebank.controller;

import myapp.ebank.model.entity.Funds;
import myapp.ebank.service.FundService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * The type Fund controller.
 */
@RestController
@RequestMapping("/fund")
@Validated
public class FundController {

    /**
     * The Fund service.
     */
    final FundService fundService;

    /**
     * Instantiates a new Fund controller.
     *
     * @param fundService the fund service
     */
    public FundController(FundService fundService) {
        this.fundService = fundService;
    }


    /**
     * Gets all funds.
     *
     * @param httpServletRequest the http servlet request
     * @return the all funds
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllFunds(HttpServletRequest httpServletRequest) {

        return fundService.listAllFunds(httpServletRequest);

    }


    /**
     * Update fund response entity.
     *
     * @param fund               the fund
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateFund(
            @Valid @RequestBody Funds fund, HttpServletRequest httpServletRequest) {

        return fundService.updateFund(fund,httpServletRequest);

    }

    /**
     * Delete fund response entity.
     *
     * @param id                 the id
     * @param httpServletRequest the http servlet request
     * @return the response entity
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteFund(
            @PathVariable Long id, HttpServletRequest httpServletRequest) {
        return fundService.deleteFund(id,httpServletRequest);
    }


}
