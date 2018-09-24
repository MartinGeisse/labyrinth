package name.martingeisse.labyrinth.game.rooms;

import name.martingeisse.labyrinth.game.Room;
import name.martingeisse.labyrinth.game.Trigger;
import name.martingeisse.labyrinth.system.SoundEffects;

public class DoorTrigger extends Trigger {

    private final RoomFactory roomFactory;
    private final int enteringDoor;

    public DoorTrigger(int x, int y, RoomFactory roomFactory, int enteringDoor) {
        super(x, y);
        this.roomFactory = roomFactory;
        this.enteringDoor = enteringDoor;
    }

    @Override
    public void onTouch(Room oldRoom) {
        SoundEffects.door.play();
        oldRoom.getGame().setRoom(roomFactory.buildRoom(enteringDoor));
    }

}
