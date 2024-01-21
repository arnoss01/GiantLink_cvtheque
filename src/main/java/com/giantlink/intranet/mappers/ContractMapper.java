package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Contract;
import com.giantlink.intranet.models.requests.ContractRequest;
import com.giantlink.intranet.models.responses.ContractResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContractMapper {
	ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

	ContractResponse contractToContractResponse(Contract contract);

	Contract contractRequestToContract(ContractRequest contractRequest);

	Set<ContractResponse> mapContract(Set<Contract> contractRequests);

	List<ContractResponse> mapContract(List<Contract> contractRequests);
}
