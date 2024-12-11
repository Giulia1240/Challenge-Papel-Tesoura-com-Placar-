package acc.br.grupodois.controler;

import acc.br.grupodois.entities.Score;
import acc.br.grupodois.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @Autowired
    private ScoreRepository scoreRepository;

    @ResponseBody
    @GetMapping("/score")
    public Score getScore() {
        Score score;
        try {
            score = scoreRepository.findById(1).get();
        } catch (Exception e) {
            score = new Score(0, 0, 0);
        }
        return score;
    }

    @GetMapping("/teste")
    public String tester(@RequestParam(name = "escolha") String escolha, Model model) {
        String saida;
        String computadorEscolha = escolhidoPeloSistema();
        Score sc = getScore();

        if (escolha.equalsIgnoreCase(computadorEscolha)) {
            saida = "Empate";
        } else if ((escolha.equalsIgnoreCase("papel") && computadorEscolha.equalsIgnoreCase("pedra")) ||
                (escolha.equalsIgnoreCase("tesoura") && computadorEscolha.equalsIgnoreCase("papel")) ||
                (escolha.equalsIgnoreCase("pedra") && computadorEscolha.equalsIgnoreCase("tesoura"))) {
            saida = "Você Ganhou";
        } else {
            saida = "Você Perdeu";
        }

        if (saida.equals("Você Ganhou")) {
            sc.setVitorias(sc.getVitorias() + 1);
        } else if (saida.equals("Você Perdeu")) {
            sc.setDerrotas(sc.getDerrotas() + 1);
        } else {
            sc.setEmpates(sc.getEmpates() + 1);
        }


        saveScore(sc);

        model.addAttribute("saida", saida);
        model.addAttribute("escolha", escolha);
        model.addAttribute("computadorEscolha", computadorEscolha);
        model.addAttribute("score", sc);

        return "result";
    }
    private String escolhidoPeloSistema() {
        String[] options = {"pedra", "papel", "tesoura"};
        int escolha = (int) (Math.random() * options.length);
        return options[escolha];
    }


    public Score saveScore(Score score){
       return scoreRepository.save(score);
    }
    @GetMapping("/scoreReset")
    public String scoreReset(Model model) {

            Score score = new Score(0, 0, 0);
            scoreRepository.save(score);

            model.addAttribute("score", score);
            model.addAttribute("saida", "Resetado!");

            return "result";
        }
    }

