package com.money.moneymanager.service;

import com.money.moneymanager.dto.ExpenseDTO;
import com.money.moneymanager.entity.ProfileEntity;
import com.money.moneymanager.repositorty.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final ExpenseService expenseService;

    @Value("${money.manager.frontend.url}")
    private String frontendUrl;


    @Scheduled(cron = "0 0 22 * * *",zone = "IST")
    public void sendDailyIncomeExpenseRemainder(){
        log.info("Job started: sendDailyIncomeExpenseRemainder()");
        List<ProfileEntity> profiles = profileRepository.findAll();
        for(ProfileEntity profile : profiles){
            String body = "Hi " + profile.getFullName() + ",<br><br>"
                    + "This is a friendly reminder to add your income and expenses for today in Money Manager.<br><br>"
                    + "<a href=\"" + frontendUrl + "\" "
                    + "style=\"display:inline-block;padding:10px 20px;"
                    + "background-color:#4CAF50;color:#ffffff;"
                    + "text-decoration:none;border-radius:5px;\">"
                    + "Go to Money Manager</a>"
                    + "<br><br>Best regards,<br>Money Manager Team";

            emailService.sendEmail(profile.getEmail(),"Daily remainder: Add your income and expense",body);
        }
    }

    @Scheduled(cron = "0 0 23 * * *",zone = "IST")
    public void sendDailyExpenseSummary(){
        log.info("Job started: sendDailyExpenseSummary()");
        List<ProfileEntity> profiles = profileRepository.findAll();
        for(ProfileEntity profile : profiles){
            List<ExpenseDTO> todaysExpenses =expenseService.getExpensesForUserOnDate(profile.getId(), LocalDate.now(ZoneId.of("Asia/Kolkata")));
            if (!todaysExpenses.isEmpty()){
                if (!todaysExpenses.isEmpty()) {
                    StringBuilder table = new StringBuilder();

                    table.append("<table style='border-collapse:collapse;width:100%;'>");

                    // Header
                    table.append("<tr style='background-color:#f2f2f2;'>")
                            .append("<th style='border:1px solid #ddd;padding:8px;'>S.No</th>")
                            .append("<th style='border:1px solid #ddd;padding:8px;'>Name</th>")
                            .append("<th style='border:1px solid #ddd;padding:8px;'>Amount</th>")
                            .append("<th style='border:1px solid #ddd;padding:8px;'>Category</th>")
                            .append("</tr>");

                    int i = 1;

                    // Body
                    for (ExpenseDTO expenseDTO : todaysExpenses) {
                        table.append("<tr>");
                        table.append("<td style='border:1px solid #ddd;padding:8px;'>")
                                .append(i++)
                                .append("</td>");
                        table.append("<td style='border:1px solid #ddd;padding:8px;'>")
                                .append(expenseDTO.getName())
                                .append("</td>");
                        table.append("<td style='border:1px solid #ddd;padding:8px;'>")
                                .append(expenseDTO.getAmount())
                                .append("</td>");
                        table.append("<td style='border:1px solid #ddd;padding:8px;'>")
                                .append(expenseDTO.getCategoryId() != null ? expenseDTO.getCategoryName() : "N/A")
                                .append("</td>");
                        table.append("</tr>");
                    }

                    table.append("</table>");
                    String body = "Hi " + profile.getFullName() + ",<br/><br/> Here is a summary of your expenses for today:<br/><br/>"+table+"<br/><br/>Best regards,<br/> Money Manager Team";
                    emailService.sendEmail(profile.getEmail(), "Your daily Expense summary",body);
                }

            }
        }
        log.info("Job completed: sendDailyExpenseSummary()");
    }

}
