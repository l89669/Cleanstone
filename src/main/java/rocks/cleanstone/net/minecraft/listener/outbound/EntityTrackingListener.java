package rocks.cleanstone.net.minecraft.listener.outbound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import rocks.cleanstone.game.entity.Entity;
import rocks.cleanstone.game.entity.HeadRotatablePosition;
import rocks.cleanstone.game.entity.LivingEntity;
import rocks.cleanstone.game.entity.RotatablePosition;
import rocks.cleanstone.game.entity.Rotation;
import rocks.cleanstone.game.entity.cleanstone.Human;
import rocks.cleanstone.game.entity.event.EntityTrackEvent;
import rocks.cleanstone.game.entity.event.EntityUntrackEvent;
import rocks.cleanstone.net.minecraft.packet.outbound.DestroyEntitiesPacket;
import rocks.cleanstone.net.minecraft.packet.outbound.SpawnMobPacket;
import rocks.cleanstone.net.minecraft.packet.outbound.SpawnPlayerPacket;
import rocks.cleanstone.player.Player;
import rocks.cleanstone.player.PlayerManager;
import rocks.cleanstone.utils.Vector;

@Slf4j
@Component
public class EntityTrackingListener {
    private final PlayerManager playerManager;

    @Autowired
    public EntityTrackingListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Async
    @EventListener
    public void onEntityTrack(EntityTrackEvent event) {
        // TODO check if the observer uses the current protocol layer
        Player observer = playerManager.getOnlinePlayer(event.getObserver());
        if (observer == null) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity instanceof Human) {
            Player player = playerManager.getOnlinePlayer(entity);
            UUID uuid = player != null ? player.getID().getUUID() : UUID.randomUUID();
            // TODO Add EntityMetadata
            observer.sendPacket(new SpawnPlayerPacket(entity.getEntityID(), uuid, entity.getPosition(), null));
        } else {
            RotatablePosition position = entity.getPosition();
            float headPitch = entity instanceof LivingEntity ?
                    ((LivingEntity) entity).getPosition().getHeadRotation().getPitch() : 0f;
            // TODO Is the mob UUID actually used?
            // TODO Add velocity
            observer.sendPacket(new SpawnMobPacket(entity.getEntityID(), UUID.randomUUID(),
                    new HeadRotatablePosition(position, new Rotation(0, headPitch)),
                    new Vector(), entity
            ));
        }
    }

    @Async
    @EventListener
    public void onEntityUntrack(EntityUntrackEvent event) {
        // TODO check if the observer uses the current protocol layer
        Player observer = playerManager.getOnlinePlayer(event.getObserver());
        if (observer == null) {
            return;
        }
        Entity entity = event.getEntity();

        observer.sendPacket(new DestroyEntitiesPacket(Collections.singletonList(entity.getEntityID())));
    }
}
