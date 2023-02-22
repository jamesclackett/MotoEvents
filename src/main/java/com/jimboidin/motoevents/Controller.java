package com.jimboidin.motoevents;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private TableView<MotoEvent> tableView;
    @FXML
    private TableColumn<MotoEvent, String> sportColumn;
    @FXML
    private TableColumn<MotoEvent, String> descriptionColumn;
    @FXML
    private TableColumn<MotoEvent, String> dateColumn;
    @FXML
    private ProgressIndicator progressIndicator;

    List<MotoEvent> motogpList, wsbkList;

    /**
     * Creates a background thread that calls the Sportradar API and
     * populates a TableView with the parsed response.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    motogpList = parse(API.getStages(API.MOTOGP_ID), "MotoGP");
                    Thread.sleep(1000); //Sportradar API limits calls to 1 per second
                    wsbkList = parse(API.getStages(API.WSBK_ID), "WSBK");

                } catch (IOException | InterruptedException e){
                    e.printStackTrace();
                }
                initializeTable(merge(motogpList, wsbkList));
                progressIndicator.setDisable(true);
                progressIndicator.setVisible(false);
                tableView.setVisible(true);
            }
        });
        thread.start();
    }

    /**
     * Merges two Lists of type MotoEvent and sorts them according to their date.
     * @param motogpList the List of MotoGP MotoEvents
     * @param wsbkList the List of WSBK MotoEvents
     * @return the merged and sorted List
     */
    private List<MotoEvent> merge(List<MotoEvent> motogpList, List<MotoEvent> wsbkList) {
        List<MotoEvent> mergeList = new ArrayList<>(motogpList.size() + wsbkList.size());
        mergeList.addAll(motogpList);
        mergeList.addAll(wsbkList);

        mergeList.sort(new Comparator<MotoEvent>() {
            public int compare(MotoEvent m1, MotoEvent m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });

        return mergeList;
    }

    /**
     * Initializes TableView and TableColumns.
     * Disables interaction with Rows.
     * @param data
     */
    private void initializeTable(List<MotoEvent> data){
        sportColumn.setCellValueFactory(new PropertyValueFactory<MotoEvent, String>("sport"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<MotoEvent, String>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<MotoEvent, String>("simpleDate"));

        ObservableList<MotoEvent> dataList = FXCollections.observableList(data);
        tableView.setItems(dataList);
        tableView.setFocusTraversable(false);
        tableView.setSelectionModel(null);
    }

    /**
     * Parses HTTPResponses received from the Sportradar MotoGP v2 API.
     * <br>Creates MotoEvents by using GSON to gather the necessary fields.
     *
     * @param response The HTTPResponse received from the API
     * @param sport The sport type to create MotoEvents as
     * @return List of all the MotoEvents created
     */
    private static List<MotoEvent> parse(HttpResponse<String> response, String sport) {
        List<MotoEvent> eventList = new ArrayList<>();

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("stages").getAsJsonArray();

        for (JsonElement element : jsonArray){
            LocalDate date;

            JsonObject obj = element.getAsJsonObject();
            String description = obj.get("description").getAsString();

            JsonArray stagesArray = obj.get("stages").getAsJsonArray();
            for (JsonElement stage : stagesArray){
                String type = stage.getAsJsonObject().get("type").getAsString();
                if (type.equals("race") || type.equals("sprint_race")) {
                    String time = stage.getAsJsonObject().get("scheduled").getAsString();
                    ZonedDateTime zdt = ZonedDateTime.parse(time);
                    date = zdt.toLocalDate();
                    eventList.add(new MotoEvent(sport, description, date));
                }
            }
        }
        return eventList;
    }
}