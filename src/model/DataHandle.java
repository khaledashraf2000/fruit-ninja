package model;

import controller.Game;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public abstract class DataHandle {
    public static Game load() {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Game.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Game game = Game.getGameInstance();
        try {
            game = (Game) unmarshaller.unmarshal(new File("input.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return game;
    }

    public static void save(Game game) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(game, new File("input.xml"));
        //TODO: current score, time, lives
    }


}
