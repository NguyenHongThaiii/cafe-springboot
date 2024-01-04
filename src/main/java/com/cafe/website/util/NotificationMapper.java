package com.cafe.website.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.cafe.website.entity.Notification;
import com.cafe.website.payload.NotificationCreateDTO;
import com.cafe.website.payload.NotificationDTO;
import com.cafe.website.payload.NotificationUpdateDTO;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {
	public NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateNotificationFromDto(NotificationDTO notificationDto, @MappingTarget Notification notifi);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
	void updateNotificationDTOFromNotification(Notification notifi, @MappingTarget NotificationDTO notificationDto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
	void updateNotificationFromNotificationUpdateDTO(NotificationUpdateDTO notifiUpdateDto,
			@MappingTarget Notification notification);

	NotificationDTO entityToDto(Notification notifi);

	Notification dtoToEntity(NotificationDTO notifiDto);

	Notification dtoToEntity(NotificationCreateDTO notifiCreateDto);

	Notification dtoToEntity(NotificationUpdateDTO notifiUpdateDto);

}
