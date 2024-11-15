package com.PigeonSkyRace.Auth.Service;

import com.PigeonSkyRace.Auth.models.Competition;
import com.PigeonSkyRace.Auth.models.CompetitionDTO;
import com.PigeonSkyRace.Auth.models.CompetitionPigeon;
import com.PigeonSkyRace.Auth.repository.CompetitionPigeonRepository;
import com.PigeonSkyRace.Auth.repository.CompetitionRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private PigeonService pigeonService;

    @Autowired
    private CompetitionPigeonRepository competitionPigeonRepository;



    // Method to add a new competition
    public Competition addCompetition(CompetitionDTO competitionDTO) {
        // Convert CompetitionDTO to Competition entity
        Competition competition = new Competition();
        competition.setName(competitionDTO.getName());
        competition.setDepartureTime(competitionDTO.getDepartureTime());
        competition.setPercentage(competitionDTO.getPercentage());
        competition.setStatus(true);
        competition.setStarted(false);

        // Save the competition to the database
        competitionRepository.save(competition);
        return competition ;
    }
    public List<Competition> fetchCompetition() {
        return competitionRepository.findAll();
    }


    public void updateCompetition(String competitionId, double latitude , double longitude , int TotalPigeon , int PigeonCount) {

        Competition existingCompetition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        existingCompetition.setPigeonTotal(TotalPigeon);
        existingCompetition.setPigeonCount(PigeonCount);
        existingCompetition.setLatitude(latitude);
        existingCompetition.setLongitude(longitude);
        existingCompetition.setStarted(true);

        competitionRepository.save(existingCompetition);
        System.out.println("competitionRepository.save(existingCompetition)" + competitionRepository.save(existingCompetition));
    }

    public void updateCompetition(Competition competition) {
        competitionRepository.save(competition);
    }

    public Competition getCompetitionByid(String competitionId) {
      return   competitionRepository.findById(competitionId).orElseThrow(() -> new RuntimeException("Competition not found"));
    }

    public String endCompetition(String competitionId) {

        Optional<Competition> competitionOpt = competitionRepository.findById(competitionId);

        if (competitionOpt.isPresent()) {

            Competition competition = competitionOpt.get();

            competition.setStatus(false);
            competition.setStarted(false);
            competitionRepository.save(competition);

            this.calculateResult(competitionId);
            return "Updated seccseflly";
        }

        return "No competition found";
    }

    public long calculateTotalSeconds(CompetitionPigeon competitionPigeon, Competition competition) {
        LocalDateTime endTime = LocalDateTime.from(competitionPigeon.getEndTime());


        LocalDateTime departureTime = competition.getDepartureTime();


        long totalSeconds = (endTime != null) ?
                Duration.between(departureTime, endTime).getSeconds() :
                Duration.between(departureTime, LocalDateTime.now()).getSeconds();

        return totalSeconds;
    }

    private void calculateResult(String competitionId){

        List<CompetitionPigeon> competitionPigeons = competitionPigeonRepository.findByCompetitionId(competitionId);


        for (CompetitionPigeon competitionPigeon : competitionPigeons) {
            System.out.println(competitionPigeon);

            long totalSeconds = this.calculateTotalSeconds(competitionPigeon, competitionPigeon.getCompetition());


            if (totalSeconds != 0) {
                double vitesse = competitionPigeon.getDistance() / totalSeconds;
                competitionPigeon.setVitesse(vitesse);
            } else {
                System.out.println("Error: EndTime is zero, cannot calculate speed");
            }


        }
        competitionPigeons.sort(new Comparator<CompetitionPigeon>() {
            @Override
            public int compare(CompetitionPigeon p1, CompetitionPigeon p2) {
                return Double.compare(p2.getVitesse(), p1.getVitesse());
            }
        });

        for (int rank = 0; rank < competitionPigeons.size(); rank++) {
            CompetitionPigeon competitionPigeon = competitionPigeons.get(rank);

            double score = 100 * (1 - (double) (rank) / (competitionPigeons.size() - 1));
            competitionPigeon.setScore(score);

            competitionPigeonRepository.save(competitionPigeon);
        }


        try{
            this.GeneratePDF(competitionPigeons);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void GeneratePDF(List<CompetitionPigeon> competitionPigeons) throws IOException {
        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Competition Pigeons");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("CL");
        headerRow.createCell(1).setCellValue("Colombier");
        headerRow.createCell(2).setCellValue("N bague");
        headerRow.createCell(3).setCellValue("Heure");
        headerRow.createCell(4).setCellValue("Distance");
        headerRow.createCell(5).setCellValue("Vitesse");
        headerRow.createCell(6).setCellValue("Point");

        // Populate the data rows
        int rowNum = 1;
        int rank = 0;

        for (;rank <  Math.ceil(competitionPigeons.size() * ( competitionPigeons.get(0).getCompetition().getPercentage() / 100.0 )) ; rank++) {
            Row row = sheet.createRow(rowNum++);

            // Fill in the values for each column
            row.createCell(0).setCellValue(rank + 1);  // CL
            row.createCell(1).setCellValue(competitionPigeons.get(rank).getPigeon().getBreeder().getNomColombie());  // Colombier
            row.createCell(2).setCellValue(competitionPigeons.get(rank).getPigeon().getRingNumber());  // N bague
            row.createCell(3).setCellValue(competitionPigeons.get(rank).getEndTime().toString());  // Heure
            row.createCell(4).setCellValue(competitionPigeons.get(rank).getDistance());  // Distance
            row.createCell(5).setCellValue(competitionPigeons.get(rank).getVitesse());  // Vitesse
            row.createCell(6).setCellValue(competitionPigeons.get(rank).getScore());  // Point

        }

        // Write the output to a file
        try (FileOutputStream fileOut = new FileOutputStream("CompetitionPigeons.xlsx")) {
            workbook.write(fileOut);
        } finally {
            workbook.close();
        }

        System.out.println("Excel file generated successfully.");
    }
}


