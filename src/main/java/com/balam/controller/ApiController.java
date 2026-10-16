package com.balam.controller;

import com.balam.entity.Enquiry;
import com.balam.entity.Review;
import com.balam.repository.EnquiryRepository;
import com.balam.repository.ReviewRepository;
import com.balam.service.NotificationService; // NEW IMPORT

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

	@Autowired
	private EnquiryRepository enquiryRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private NotificationService notificationService; // NEW

	// ENQUIRIES
	@GetMapping("/enquiries")
	public List<Enquiry> getEnquiries() {
		return enquiryRepository.findAll();
	}

	@PostMapping("/enquiries")
	public Enquiry createEnquiry(@RequestBody Enquiry enquiry) {
		Enquiry saved = enquiryRepository.save(enquiry);

		// 🔥 NOTIFICATION to 9666667667 & 7671975794 - Ventane vastadi
		try {
			notificationService.sendInstantNotification(enquiry.getPhone(), enquiry.getFromCity(), enquiry.getToCity(),
					enquiry.getSource() != null ? enquiry.getSource() : "website_popup");
		} catch (Exception e) {
			System.out.println("Notification failed: " + e.getMessage());
		}

		return saved;
	}

	@DeleteMapping("/enquiries/{id}")
	public void deleteEnquiry(@PathVariable Long id) {
		enquiryRepository.deleteById(id);
	}

	// REVIEWS
	@GetMapping("/reviews")
	public List<Review> getReviews() {
		return reviewRepository.findAll();
	}

	@PostMapping("/reviews")
	public Review createReview(@RequestBody Review review) {
		return reviewRepository.save(review);
	}

	@DeleteMapping("/reviews/{id}")
	public void deleteReview(@PathVariable Long id) {
		reviewRepository.deleteById(id);
	}

	@PutMapping("/reviews/{id}")
	public Review updateReview(@PathVariable Long id, @RequestBody Review review) {
		review.setId(id);
		return reviewRepository.save(review);
	}

	// NOTIFY LEAD - Direct call from frontend also
	@GetMapping("/notify-lead")
	public String notifyLead(@RequestParam String phone, @RequestParam(required = false) String from,
			@RequestParam(required = false) String to) {
		notificationService.sendInstantNotification(phone, from, to, "direct_notify");
		return "Notification sent to 9666667667 & 7671975794 for " + phone;
	}
}