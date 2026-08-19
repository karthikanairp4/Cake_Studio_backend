package com.example.thecakestudio.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.thecakestudio.dto.BuyNowRequestDTO;
import com.example.thecakestudio.dto.CheckoutDTO;
import com.example.thecakestudio.entity.CakeOptions;
import com.example.thecakestudio.entity.Cakes;
import com.example.thecakestudio.entity.CartItem;
import com.example.thecakestudio.entity.Order;
import com.example.thecakestudio.entity.OrderItem;
import com.example.thecakestudio.entity.User;
import com.example.thecakestudio.enums.CakeCategory;
import com.example.thecakestudio.enums.OptionType;
import com.example.thecakestudio.enums.OrderStatus;
import com.example.thecakestudio.enums.PaymentStatus;
import com.example.thecakestudio.replository.CakeOptionsRepo;
import com.example.thecakestudio.replository.CakeRepo;
import com.example.thecakestudio.replository.CartItemRepo;
import com.example.thecakestudio.replository.OrderItemRepo;
import com.example.thecakestudio.replository.OrderRepo;
import com.example.thecakestudio.replository.UserRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private OrderItemRepo orderItemRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CakeRepo cakeRepo;
	
	@Autowired
	private CakeOptionsRepo optionRepo;


	public Order checkOut(CheckoutDTO checkoutDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		System.out.println(email);
		User user = userRepo.findByEmail(email);
		List<CartItem> cartItems = cartItemRepo.findByUser(user);

		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(LocalDateTime.now());
		order.setStreet(checkoutDTO.getStreet());
		order.setCity(checkoutDTO.getCity());
		order.setProvince(checkoutDTO.getProvince());
		order.setPostalCode(checkoutDTO.getPostalCode());
		order.setStatus(OrderStatus.PENDING);
		order.setPaymentStatus(PaymentStatus.SUCCESS);
		order.setPaymentMethod("CARD");
		order.setPickupDate(checkoutDTO.getPickupDate());
		order.setPickupTime(checkoutDTO.getPickupTime());
		order.setNotes(checkoutDTO.getNotes());
		order = orderRepo.save(order);

		double grandTotal = 0;

		for (CartItem cart : cartItems) {
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setCake(cart.getCake());
			item.setQuantity(cart.getQuantity());
			item.setWeight(cart.getWeight());
			item.setMessage(cart.getMessage());
			item.setSponge(cart.getSponge());
			item.setFilling(cart.getFilling());
			item.setFrosting(cart.getFrosting());

			double price = calculatePrice(cart.getCake(), cart.getSponge(), cart.getFilling(), cart.getFrosting(),
					cart.getWeight(), cart.getQuantity());

			item.setPrice(price);
			grandTotal += price;
			orderItemRepo.save(item);
		}

		order.setTotalAmount(grandTotal);
		orderRepo.save(order);
		cartItemRepo.deleteAll(cartItems);

		return order;

	}

	public double calculatePrice(Cakes cake, CakeOptions sponge, CakeOptions filling, CakeOptions frosting,
			double weight, int quantity) {

		double total = cake.getBasePrice() * weight;

		if (sponge != null)
			total += sponge.getPrice();

		if (filling != null)
			total += filling.getPrice();

		if (frosting != null)
			total += frosting.getPrice();

		if (cake.getCategory() == CakeCategory.THEMED)
			total += 10;

		return total * quantity;
	}

	public Order buyNow(BuyNowRequestDTO dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepo.findByEmail(email);
		Cakes cake = cakeRepo.findById(dto.getCakeId()).orElseThrow();
		CakeOptions sponge = dto.getSpongeId() == null ? null : optionRepo.findById(dto.getSpongeId()).orElse(null);
		CakeOptions filling = dto.getFillingId() == null ? null : optionRepo.findById(dto.getFillingId()).orElse(null);
		CakeOptions frosting = dto.getFrostingId() == null ? null : optionRepo.findById(dto.getFrostingId()).orElse(null);

		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(LocalDateTime.now());
		order.setStreet(dto.getStreet());
		order.setCity(dto.getCity());
		order.setProvince(dto.getProvince());
		order.setPostalCode(dto.getPostalCode());
		order.setStatus(OrderStatus.PENDING);
		order.setPaymentStatus(PaymentStatus.SUCCESS);
		order.setPickupDate(dto.getPickupDate());
		order.setPickupTime(dto.getPickupTime());
		order.setNotes(dto.getNotes());
		order = orderRepo.save(order);

		OrderItem item = new OrderItem();
		item.setOrder(order);
		item.setCake(cake);
		item.setQuantity(dto.getQuantity());
		item.setWeight(dto.getWeight());
		item.setMessage(dto.getMessage());
		item.setSponge(sponge);
		item.setFilling(filling);
		item.setFrosting(frosting);

		double price = calculatePrice(cake, sponge, filling, frosting, dto.getWeight(),
				dto.getQuantity());

		item.setPrice(price);
		orderItemRepo.save(item);
		order.setTotalAmount(price);
		orderRepo.save(order);
		return order;
	}
	
	public List<Order> getOrders() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String email = authentication.getName();
	    User user = userRepo.findByEmail(email);
	    return orderRepo.findByUserOrderByOrderDateDesc(user);
	}
	
	public Order getOrder(Integer id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String email = authentication.getName();
	    User user = userRepo.findByEmail(email);
	    Order order = orderRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    if (!order.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("Unauthorized");
	    }
	    return order;
	}

	public Order cancelOrder(Integer orderId) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String email = authentication.getName();
	    User user = userRepo.findByEmail(email);
	    Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

	    // Make sure the order belongs to this customer
	    if (!order.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("Unauthorized");
	    }

	    // Already cancelled
	    if (order.getStatus() == OrderStatus.CANCELLED) {
	        throw new RuntimeException("Order is already cancelled");
	    }

	    // Already completed
	    if (order.getStatus() == OrderStatus.COMPLETED) {
	        throw new RuntimeException("Completed orders cannot be cancelled");
	    }

	    // Pickup date is required
	    if (order.getPickupDate() == null) {
	        throw new RuntimeException(
	                "Order cannot be cancelled because pickup date is not available"
	        );
	    }

	    LocalDate today = LocalDate.now();
	    LocalDate cancellationDeadline = order.getPickupDate().minusDays(2);

	    // Cancellation deadline has passed
	    if (today.isAfter(cancellationDeadline)) {
	        throw new RuntimeException(
	                "This order can no longer be cancelled. " +
	                "Orders must be cancelled at least 2 days before pickup."
	        );
	    }

	    order.setStatus(OrderStatus.CANCELLED);
	    return orderRepo.save(order);
	}

}
