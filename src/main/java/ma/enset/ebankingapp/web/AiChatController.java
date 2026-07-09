package ma.enset.ebankingapp.web;

import lombok.AllArgsConstructor;
import ma.enset.ebankingapp.ai.BankingChatService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@AllArgsConstructor
public class AiChatController {

    private BankingChatService bankingChatService;

    @PostMapping("/chat")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        String response = bankingChatService.chat(userMessage);
        return Map.of("response", response);
    }
}
